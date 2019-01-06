package de.btobastian.javacord.utils;

import com.google.common.util.concurrent.SettableFuture;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.utils.handler.ReadyHandler;
import de.btobastian.javacord.utils.handler.ReadyReconnectHandler;
import de.btobastian.javacord.utils.handler.channel.ChannelCreateHandler;
import de.btobastian.javacord.utils.handler.channel.ChannelDeleteHandler;
import de.btobastian.javacord.utils.handler.channel.ChannelUpdateHandler;
import de.btobastian.javacord.utils.handler.message.MessageAckHandler;
import de.btobastian.javacord.utils.handler.message.MessageCreateHandler;
import de.btobastian.javacord.utils.handler.message.MessageDeleteHandler;
import de.btobastian.javacord.utils.handler.message.MessageUpdateHandler;
import de.btobastian.javacord.utils.handler.message.TypingStartHandler;
import de.btobastian.javacord.utils.handler.server.GuildBanAddHandler;
import de.btobastian.javacord.utils.handler.server.GuildBanRemoveHandler;
import de.btobastian.javacord.utils.handler.server.GuildCreateHandler;
import de.btobastian.javacord.utils.handler.server.GuildDeleteHandler;
import de.btobastian.javacord.utils.handler.server.GuildMemberAddHandler;
import de.btobastian.javacord.utils.handler.server.GuildMemberRemoveHandler;
import de.btobastian.javacord.utils.handler.server.GuildMemberUpdateHandler;
import de.btobastian.javacord.utils.handler.server.GuildUpdateHandler;
import de.btobastian.javacord.utils.handler.server.role.GuildRoleCreateHandler;
import de.btobastian.javacord.utils.handler.server.role.GuildRoleDeleteHandler;
import de.btobastian.javacord.utils.handler.server.role.GuildRoleUpdateHandler;
import de.btobastian.javacord.utils.handler.user.PresenceUpdateHandler;
import de.btobastian.javacord.utils.handler.user.UserGuildSettingsUpdateHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import javax.net.ssl.SSLContext;
import org.json.JSONObject;
import org.slf4j.Logger;

public class DiscordWebsocketAdapter
  extends WebSocketAdapter
{
  private static final Logger logger = LoggerUtil.getLogger(DiscordWebsocketAdapter.class);
  private WebSocket socket = null;
  private final SettableFuture<Boolean> ready;
  private final ImplDiscordAPI api;
  private final HashMap<String, PacketHandler> handlers = new HashMap();
  private final boolean isReconnect;
  private volatile boolean isClosed = false;
  private String urlForReconnect = null;
  
  public DiscordWebsocketAdapter(URI serverURI, ImplDiscordAPI api, boolean reconnect)
  {
    this.api = api;
    this.ready = SettableFuture.create();
    registerHandlers();
    this.isReconnect = reconnect;
    
    WebSocketFactory factory = new WebSocketFactory();
    try
    {
      factory.setSSLContext(SSLContext.getDefault());
    }
    catch (NoSuchAlgorithmException e)
    {
      logger.warn("An error occurred while setting ssl context", e);
    }
    try
    {
      this.socket = factory.createSocket(serverURI);
      this.socket.addListener(this);
      this.socket.connect();
    }
    catch (IOException|WebSocketException e)
    {
      logger.warn("An error occurred while connecting to websocket", e);
    }
  }
  
  public void onConnected(WebSocket socket, Map<String, List<String>> headers)
    throws Exception
  {
    JSONObject connectPacket = new JSONObject().put("op", 2).put("d", new JSONObject().put("token", this.api.getToken()).put("v", 3).put("properties", new JSONObject().put("$os", System.getProperty("os.name")).put("$browser", "None").put("$device", "").put("$referrer", "https://discordapp.com/@me").put("$referring_domain", "discordapp.com")).put("large_threshold", 250).put("compress", true));
    
    logger.debug("Sending connect packet");
    socket.sendText(connectPacket.toString());
  }
  
  public void onDisconnected(WebSocket socket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer)
    throws Exception
  {
    logger.info("Websocket closed with reason {} and code {}", serverCloseFrame != null ? serverCloseFrame.getCloseReason() : "unknown", serverCloseFrame != null ? Integer.valueOf(serverCloseFrame.getCloseCode()) : "unknown");
    
    this.isClosed = true;
    if ((closedByServer) && (this.urlForReconnect != null))
    {
      logger.info("Trying to reconnect (we received op 7 before)...");
      this.api.reconnectBlocking(this.urlForReconnect);
      logger.info("Reconnected!");
    }
    else if ((closedByServer) && (this.api.isAutoReconnectEnabled()))
    {
      logger.info("Trying to auto-reconnect...");
      this.api.reconnectBlocking();
      logger.info("Reconnected!");
    }
    if (!this.ready.isDone()) {
      this.ready.set(Boolean.valueOf(false));
    }
  }
  
  public void onConnectError(WebSocket socket, WebSocketException exception)
    throws Exception
  {
    logger.warn("Could not connect to websocket!", exception);
  }
  
  public void onError(WebSocket socket, WebSocketException cause)
    throws Exception
  {
    logger.warn("Websocket error!", cause);
  }
  
  public void onMessageError(WebSocket socket, WebSocketException cause, List<WebSocketFrame> frames)
    throws Exception
  {
    logger.warn("Websocket message error!", cause);
  }
  
  public void onSendError(WebSocket socket, WebSocketException cause, WebSocketFrame frame)
    throws Exception
  {
    logger.warn("Websocket error!", cause);
  }
  
  public void onSendingHandshake(WebSocket socket, String requestLine, List<String[]> headers)
    throws Exception
  {
    Thread.currentThread().setName("Websocket");
  }
  
  public void onTextMessage(WebSocket socket, String text)
    throws Exception
  {
    JSONObject obj = new JSONObject(text);
    
    int op = obj.getInt("op");
    if (op == 7)
    {
      this.urlForReconnect = obj.getJSONObject("d").getString("url");
      return;
    }
    JSONObject packet = obj.getJSONObject("d");
    String type = obj.getString("t");
    if ((type.equals("READY")) && (this.isReconnect))
    {
      ((PacketHandler)this.handlers.get("READY_RECONNECT")).handlePacket(packet);
      this.ready.set(Boolean.valueOf(true));
      updateStatus();
      return;
    }
    PacketHandler handler = (PacketHandler)this.handlers.get(type);
    if (handler != null) {
      handler.handlePacket(packet);
    } else {
      logger.debug("Received unknown packet of type {} (packet: {})", type, obj.toString());
    }
    if (type.equals("READY"))
    {
      this.ready.set(Boolean.valueOf(true));
      logger.debug("Received READY-packet!");
      updateStatus();
    }
  }
  
  public void onBinaryMessage(WebSocket socket, byte[] binary)
    throws Exception
  {
    Inflater decompressor = new Inflater();
    decompressor.setInput(binary);
    ByteArrayOutputStream bos = new ByteArrayOutputStream(binary.length);
    byte[] buf = new byte['Ѐ'];
    while (!decompressor.finished())
    {
      int count;
      try
      {
        count = decompressor.inflate(buf);
      }
      catch (DataFormatException e)
      {
        logger.warn("An error occurred while decompressing data", e);
        return;
      }
      bos.write(buf, 0, count);
    }
    try
    {
      bos.close();
    }
    catch (IOException ignored) {}
    byte[] decompressedData = bos.toByteArray();
    try
    {
      onTextMessage(socket, new String(decompressedData, "UTF-8"));
    }
    catch (UnsupportedEncodingException e)
    {
      logger.warn("An error occurred while decompressing data", e);
    }
  }
  
  public WebSocket getWebSocket()
  {
    return this.socket;
  }
  
  public Future<Boolean> isReady()
  {
    return this.ready;
  }
  
  public void startHeartbeat(final long heartbeatInterval)
  {
    this.api.getThreadPool().getExecutorService().submit(new Runnable()
    {
      public void run()
      {
        long timer = System.currentTimeMillis();
        while (!DiscordWebsocketAdapter.this.isClosed)
        {
          if (System.currentTimeMillis() - timer >= heartbeatInterval - 10L)
          {
            JSONObject heartbeat = new JSONObject().put("op", 1).put("d", System.currentTimeMillis());
            
            DiscordWebsocketAdapter.this.socket.sendText(heartbeat.toString());
            timer = System.currentTimeMillis();
            DiscordWebsocketAdapter.logger.debug("Sending heartbeat (interval: {})", Long.valueOf(heartbeatInterval));
            if (Math.random() < 0.1D) {
              DiscordWebsocketAdapter.this.updateStatus();
            }
          }
          try
          {
            Thread.sleep(10L);
          }
          catch (InterruptedException e)
          {
            DiscordWebsocketAdapter.logger.warn("An error occurred while sending heartbeat", e);
          }
        }
      }
    });
  }
  
  public void updateStatus()
  {
    logger.debug("Updating status (game: {}, idle: {})", this.api.getGame() == null ? "none" : this.api.getGame(), Boolean.valueOf(this.api.isIdle()));
    
    JSONObject updateStatus = new JSONObject().put("op", 3).put("d", new JSONObject().put("game", new JSONObject().put("name", this.api.getGame() == null ? JSONObject.NULL : this.api.getGame())).put("idle_since", this.api.isIdle() ? Integer.valueOf(1) : JSONObject.NULL));
    
    this.socket.sendText(updateStatus.toString());
  }
  
  private void registerHandlers()
  {
    addHandler(new ReadyHandler(this.api));
    addHandler(new ReadyReconnectHandler(this.api));
    
    addHandler(new ChannelCreateHandler(this.api));
    addHandler(new ChannelDeleteHandler(this.api));
    addHandler(new ChannelUpdateHandler(this.api));
    
    addHandler(new MessageAckHandler(this.api));
    addHandler(new MessageCreateHandler(this.api));
    addHandler(new MessageDeleteHandler(this.api));
    addHandler(new MessageUpdateHandler(this.api));
    addHandler(new TypingStartHandler(this.api));
    
    addHandler(new GuildBanAddHandler(this.api));
    addHandler(new GuildBanRemoveHandler(this.api));
    addHandler(new GuildCreateHandler(this.api));
    addHandler(new GuildDeleteHandler(this.api));
    addHandler(new GuildMemberAddHandler(this.api));
    addHandler(new GuildMemberRemoveHandler(this.api));
    addHandler(new GuildMemberUpdateHandler(this.api));
    addHandler(new GuildUpdateHandler(this.api));
    
    addHandler(new GuildRoleCreateHandler(this.api));
    addHandler(new GuildRoleDeleteHandler(this.api));
    addHandler(new GuildRoleUpdateHandler(this.api));
    
    addHandler(new PresenceUpdateHandler(this.api));
    addHandler(new UserGuildSettingsUpdateHandler(this.api));
  }
  
  private void addHandler(PacketHandler handler)
  {
    this.handlers.put(handler.getType(), handler);
  }
}
