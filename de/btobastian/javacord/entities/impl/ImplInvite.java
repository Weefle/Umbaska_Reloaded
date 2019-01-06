package de.btobastian.javacord.entities.impl;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Invite;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.utils.LoggerUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Future;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplInvite
  implements Invite
{
  private static final Logger logger = LoggerUtil.getLogger(ImplInvite.class);
  private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
  private static final SimpleDateFormat FORMAT_ALTERNATIVE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  private static final SimpleDateFormat FORMAT_ALTERNATIVE_TWO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
  private final ImplDiscordAPI api;
  private final String code;
  private final String serverId;
  private final String serverName;
  private final String channelId;
  private final String channelName;
  private final boolean voice;
  private int maxAge = -1;
  private boolean revoked = false;
  private Calendar creationDate = null;
  private int uses = -1;
  private int maxUses = -1;
  private boolean temporary = false;
  private User creator = null;
  
  public ImplInvite(ImplDiscordAPI api, JSONObject data)
  {
    this.api = api;
    this.code = data.getString("code");
    this.serverId = data.getJSONObject("guild").getString("id");
    this.serverName = data.getJSONObject("guild").getString("name");
    this.channelId = data.getJSONObject("channel").getString("id");
    this.channelName = data.getJSONObject("channel").getString("name");
    this.voice = (!data.getJSONObject("channel").getString("type").equals("text"));
    if (data.has("max_age")) {
      this.maxAge = data.getInt("max_age");
    }
    if (data.has("revoked")) {
      this.revoked = data.getBoolean("revoked");
    }
    if (data.has("created_at"))
    {
      String time = data.getString("created_at");
      Calendar calendar = Calendar.getInstance();
      synchronized (FORMAT)
      {
        try
        {
          calendar.setTime(FORMAT.parse(time.substring(0, time.length() - 9)));
        }
        catch (ParseException ignored)
        {
          try
          {
            calendar.setTime(FORMAT_ALTERNATIVE.parse(time.substring(0, time.length() - 9)));
          }
          catch (ParseException ignored2)
          {
            try
            {
              calendar.setTime(FORMAT_ALTERNATIVE_TWO.parse(time.substring(0, time.length() - 9)));
            }
            catch (ParseException e)
            {
              logger.warn("Could not parse timestamp {}. Please contact the developer!", time, e);
            }
          }
        }
      }
      this.creationDate = calendar;
    }
    if (data.has("temporary")) {
      this.temporary = data.getBoolean("temporary");
    }
    if (data.has("uses")) {
      this.uses = data.getInt("uses");
    }
    if (data.has("max_uses"))
    {
      this.maxUses = data.getInt("max_uses");
      if (this.maxUses == 0) {
        this.maxUses = -1;
      }
    }
    if (data.has("inviter")) {
      this.creator = api.getOrCreateUser(data.getJSONObject("inviter"));
    }
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public URL getInviteUrl()
  {
    try
    {
      return new URL("https://discord.gg/" + this.code);
    }
    catch (MalformedURLException e)
    {
      logger.warn("Malformed invite url of invite code {}! Please contact the developer!", this.code, e);
    }
    return null;
  }
  
  public String getServerId()
  {
    return this.serverId;
  }
  
  public String getServerName()
  {
    return this.serverName;
  }
  
  public Server getServer()
  {
    return this.api.getServerById(this.serverId);
  }
  
  public String getChannelId()
  {
    return this.channelId;
  }
  
  public String getChannelName()
  {
    return this.channelName;
  }
  
  public Channel getChannel()
  {
    Server server = getServer();
    return server == null ? null : server.getChannelById(this.channelId);
  }
  
  public VoiceChannel getVoiceChannel()
  {
    Server server = getServer();
    return server == null ? null : server.getVoiceChannelById(this.channelId);
  }
  
  public boolean isVoiceChannel()
  {
    return this.voice;
  }
  
  public int getMaxAge()
  {
    return this.maxAge;
  }
  
  public boolean isRevoked()
  {
    return this.revoked;
  }
  
  public Calendar getCreationDate()
  {
    if (this.creationDate == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(this.creationDate.getTime());
    return calendar;
  }
  
  public int getUses()
  {
    return this.uses;
  }
  
  public int getMaxUses()
  {
    return this.maxUses;
  }
  
  public boolean isTemporary()
  {
    return this.temporary;
  }
  
  public User getCreator()
  {
    return this.creator;
  }
  
  public Future<Server> acceptInvite()
  {
    return acceptInvite(null);
  }
  
  public Future<Server> acceptInvite(FutureCallback<Server> callback)
  {
    return this.api.acceptInvite(getCode(), callback);
  }
  
  public Future<Exception> delete()
  {
    return this.api.deleteInvite(getCode());
  }
  
  public String toString()
  {
    return getCode();
  }
  
  public int hashCode()
  {
    return getCode().hashCode();
  }
}
