package de.btobastian.javacord.entities.impl;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Application;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.utils.LoggerUtil;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplApplication
  implements Application
{
  private static final Logger logger = LoggerUtil.getLogger(ImplApplication.class);
  private final ImplDiscordAPI api;
  private final String id;
  private final String description;
  private final String[] redirectUris;
  private final String name;
  private final String secret;
  private String botToken;
  private User bot;
  
  public ImplApplication(ImplDiscordAPI api, JSONObject data)
  {
    this.api = api;
    
    this.id = data.getString("id");
    this.description = data.getString("description");
    JSONArray jsonRedirectUris = data.getJSONArray("redirect_uris");
    this.redirectUris = new String[jsonRedirectUris.length()];
    for (int i = 0; i < this.redirectUris.length; i++) {
      this.redirectUris[i] = jsonRedirectUris.getString(i);
    }
    this.name = data.getString("name");
    this.secret = data.getString("secret");
    if (data.has("bot"))
    {
      this.botToken = data.getJSONObject("bot").getString("token");
      this.bot = api.getOrCreateUser(data.getJSONObject("bot"));
    }
    else
    {
      this.botToken = null;
      this.bot = null;
    }
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String[] getRedirectUris()
  {
    return this.redirectUris;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getSecret()
  {
    return this.secret;
  }
  
  public String getBotToken()
  {
    return this.botToken;
  }
  
  public User getBot()
  {
    return this.bot;
  }
  
  public Future<Exception> delete()
  {
    return this.api.deleteApplication(getId());
  }
  
  public void setBot(User bot)
  {
    this.bot = bot;
  }
  
  public void setBotToken(String botToken)
  {
    this.botToken = botToken;
  }
  
  public String toString()
  {
    return getName() + " (id: " + getId() + ")";
  }
}
