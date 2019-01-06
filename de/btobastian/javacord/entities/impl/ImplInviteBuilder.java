package de.btobastian.javacord.entities.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Invite;
import de.btobastian.javacord.entities.InviteBuilder;
import de.btobastian.javacord.utils.LoggerUtil;
import de.btobastian.javacord.utils.ThreadPool;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplInviteBuilder
  implements InviteBuilder
{
  private static final Logger logger = LoggerUtil.getLogger(ImplInviteBuilder.class);
  private final ImplDiscordAPI api;
  private final ImplChannel textChannel;
  private final ImplVoiceChannel voiceChannel;
  private int maxUses = -1;
  private byte temporary = -1;
  private int maxAge = -1;
  
  public ImplInviteBuilder(ImplChannel textChannel, ImplDiscordAPI api)
  {
    this.textChannel = textChannel;
    this.voiceChannel = null;
    this.api = api;
  }
  
  public ImplInviteBuilder(ImplVoiceChannel voiceChannel, ImplDiscordAPI api)
  {
    this.textChannel = null;
    this.voiceChannel = voiceChannel;
    this.api = api;
  }
  
  public InviteBuilder setMaxUses(int maxUses)
  {
    this.maxUses = maxUses;
    return this;
  }
  
  public InviteBuilder setTemporary(boolean temporary)
  {
    this.temporary = (temporary ? 1 : 0);
    return this;
  }
  
  public InviteBuilder setMaxAge(int maxAge)
  {
    this.maxAge = maxAge;
    return this;
  }
  
  public Future<Invite> create()
  {
    return create(null);
  }
  
  public Future<Invite> create(FutureCallback<Invite> callback)
  {
    ListenableFuture<Invite> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Invite call()
        throws Exception
      {
        ImplInviteBuilder.logger.debug("Trying to create invite for channel {} (max uses: {}, temporary: {}, max age: {}", new Object[] { ImplInviteBuilder.this.textChannel == null ? ImplInviteBuilder.this.voiceChannel : ImplInviteBuilder.this.textChannel, Integer.valueOf(ImplInviteBuilder.this.maxUses), Byte.valueOf(ImplInviteBuilder.this.temporary), Integer.valueOf(ImplInviteBuilder.this.maxAge) });
        
        JSONObject jsonParam = new JSONObject();
        if (ImplInviteBuilder.this.maxUses > 0) {
          jsonParam.put("max_uses", ImplInviteBuilder.this.maxUses);
        }
        if (ImplInviteBuilder.this.temporary > -1) {
          jsonParam.put("temporary", ImplInviteBuilder.this.temporary == 1);
        }
        if (ImplInviteBuilder.this.maxAge > 0) {
          jsonParam.put("max_age", ImplInviteBuilder.this.maxAge);
        }
        String channelId = ImplInviteBuilder.this.textChannel == null ? ImplInviteBuilder.this.voiceChannel.getId() : ImplInviteBuilder.this.textChannel.getId();
        HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/channels/" + channelId + "/invites").header("authorization", ImplInviteBuilder.this.api.getToken()).header("Content-Type", "application/json").body(jsonParam.toString()).asJson();
        
        ImplInviteBuilder.this.api.checkResponse(response);
        JSONObject data = ((JsonNode)response.getBody()).getObject();
        ImplInviteBuilder.logger.debug("Created invite for channel {} (max uses: {}, temporary: {}, max age: {}", new Object[] { ImplInviteBuilder.this.textChannel == null ? ImplInviteBuilder.this.voiceChannel : ImplInviteBuilder.this.textChannel, Integer.valueOf(ImplInviteBuilder.this.maxUses), Byte.valueOf(ImplInviteBuilder.this.temporary), Integer.valueOf(data.has("max_age") ? data.getInt("max_age") : -1) });
        
        return new ImplInvite(ImplInviteBuilder.this.api, data);
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
}
