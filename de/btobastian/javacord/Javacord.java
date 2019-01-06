package de.btobastian.javacord;

import com.mashape.unirest.http.Unirest;
import de.btobastian.javacord.utils.ThreadPool;

public class Javacord
{
  public static final String VERSION = "2.0.10";
  public static final String GITHUB_URL = "https://github.com/BtoBastian/Javacord";
  public static final String USER_AGENT = "Javacord DiscordBot (https://github.com/BtoBastian/Javacord, v2.0.10)";
  
  static
  {
    Unirest.setDefaultHeader("User-Agent", "Javacord DiscordBot (https://github.com/BtoBastian/Javacord, v2.0.10)");
  }
  
  public static DiscordAPI getApi()
  {
    return new ImplDiscordAPI(new ThreadPool());
  }
  
  public static DiscordAPI getApi(String email, String password)
  {
    DiscordAPI api = getApi();
    api.setEmail(email);
    api.setPassword(password);
    return api;
  }
  
  public static DiscordAPI getApi(String token, boolean bot)
  {
    DiscordAPI api = getApi();
    api.setToken(token, bot);
    return api;
  }
}
