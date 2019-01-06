package uk.co.umbaska.LargeSk.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Pastebin
{
  private static final String USER_AGENT = "Mozilla/5.0";
  
  public static String sendPost(String textToPaste, @Nullable String nameOfPaste, @Nullable String expireDate, @Nullable String pasteFormat)
    throws Exception
  {
    String url = "http://pastebin.com/api/api_post.php";
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection)obj.openConnection();
    
    con.setRequestMethod("POST");
    con.setRequestProperty("User-Agent", "Mozilla/5.0");
    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    
    textToPaste = URLEncoder.encode(textToPaste, "UTF-8");
    
    String urlParameters = "api_option=paste&api_dev_key=" + Bukkit.getPluginManager().getPlugin("LargeSk").getConfig().getString("pastebinDeveloperKey") + "&api_paste_private=0" + "&api_paste_code=" + textToPaste;
    if (nameOfPaste != null)
    {
      nameOfPaste = URLEncoder.encode(nameOfPaste, "UTF-8");
      urlParameters = urlParameters + "&api_paste_name=" + nameOfPaste;
    }
    if (expireDate != null) {
      urlParameters = urlParameters + "&api_paste_expire_date=" + expireDate;
    }
    if (pasteFormat != null) {
      urlParameters = urlParameters + "&api_paste_format=" + pasteFormat;
    }
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();
    
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    
    StringBuffer response = new StringBuffer();
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    
    return response.toString();
  }
}
