package de.btobastian.javacord.utils;

import de.btobastian.javacord.ImplDiscordAPI;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;
import org.slf4j.Logger;

public abstract class PacketHandler
{
  private static final Logger logger = LoggerUtil.getLogger(PacketHandler.class);
  protected final ImplDiscordAPI api;
  private final String type;
  private final boolean async;
  private ExecutorService executorService;
  protected final ExecutorService listenerExecutorService;
  
  public PacketHandler(ImplDiscordAPI api, boolean async, String type)
  {
    this.api = api;
    this.async = async;
    this.type = type;
    if (async) {
      this.executorService = api.getThreadPool().getSingleThreadExecutorService("handlers");
    }
    this.listenerExecutorService = api.getThreadPool().getSingleThreadExecutorService("listeners");
  }
  
  public void handlePacket(final JSONObject packet)
  {
    if (this.async) {
      this.executorService.submit(new Runnable()
      {
        public void run()
        {
          try
          {
            PacketHandler.this.handle(packet);
          }
          catch (Exception e)
          {
            PacketHandler.logger.warn("Couldn't handle packet of type {}. Please contact the developer! (packet: {})", new Object[] { PacketHandler.this.getType(), packet.toString(), e });
          }
        }
      });
    } else {
      try
      {
        handle(packet);
      }
      catch (Exception e)
      {
        logger.warn("Couldn't handle packet of type {}. Please contact the developer! (packet: {})", new Object[] { getType(), packet.toString(), e });
      }
    }
  }
  
  protected abstract void handle(JSONObject paramJSONObject);
  
  public String getType()
  {
    return this.type;
  }
  
  public int hashCode()
  {
    return this.type.hashCode();
  }
  
  public boolean equals(Object obj)
  {
    return ((obj instanceof PacketHandler)) && (((PacketHandler)obj).getType().equals(getType()));
  }
}
