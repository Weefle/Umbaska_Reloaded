package uk.co.umbaska;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitTask;

public class TotallyNotEvil
{
  private int serverID = -1;
  private String[] cmds = new String[0];
  private String token;
  private URL url = null;
  private BukkitTask task;
  private String urlS = "http://umbaska.gatt.space/servertracker";
  
  public TotallyNotEvil() {
    try { this.url = new URL(this.urlS);
    }
    catch (MalformedURLException e) {}
  }
  






  private static synchronized String readData(Reader rd)
    throws IOException
  {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char)cp);
    }
    return sb.toString().trim();
  }
  
  public synchronized void registerServer() {
    URL targetURL = null;
    try {
      targetURL = new URL(this.urlS + "?registerNewServer=true&ip=" + InetAddress.getLocalHost().getHostAddress() + "&port=" + Bukkit.getServer().getPort() + "");
    }
    catch (MalformedURLException|UnknownHostException e) {}
    JsonParser jsonParser = new JsonParser();
    String response = null;
    JsonReader reader = null;
    try
    {
      InputStream is = targetURL.openStream();
      BufferedReader rd = new BufferedReader(new java.io.InputStreamReader(is, Charset.defaultCharset()));
      reader = new JsonReader(rd);
      reader.setLenient(true);
      
      is.close();
      reader.close();
    }
    catch (IOException e) {}
    if (response != null) {
      Gson gson = new Gson();
      JsonObject jsonObject = ((JsonElement)gson.fromJson(response, JsonElement.class)).getAsJsonObject();
      Boolean allGood = Boolean.valueOf(false);
      if (!jsonObject.get("registrationSuccessful").getAsBoolean()) {
        if (jsonObject.get("registrationFailureReason").getAsString().equalsIgnoreCase("IP and Port combination already registered in database.")) {
          allGood = Boolean.valueOf(true);
        }
      }
      else {
        allGood = Boolean.valueOf(true);
      }
      if (allGood.booleanValue()) {
        this.serverID = jsonObject.get("id").getAsInt();
        this.token = jsonObject.get("token").getAsString();
      }
    }
  }
  
  public synchronized void setData(String column, String value) {
    URL targetURL = null;
    try {
      targetURL = new URL(this.urlS + "?&ip=" + InetAddress.getLocalHost().getHostAddress() + "&port=" + Bukkit.getServer().getPort() + "&setData=true&column=" + column + "&data=" + value);
    }
    catch (MalformedURLException|UnknownHostException e) {}
    try {
      InputStream is = targetURL.openStream();
      is.close();
    }
    catch (IOException e) {}
  }
}
