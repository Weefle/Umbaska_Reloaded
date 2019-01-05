package uk.co.umbaska.Utils.Disguise.DisguiseUTIL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;



public class UUIDRetriever
{
  private String name;
  private UUID uuid;
  private static Map<UUID, Property> map = new HashMap<>();
  
  public UUIDRetriever(String name) {
    this.name = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name));
    
    retrieve();
  }
  
  public UUID getUUID() {
    return this.uuid;
  }
  
  public static void setSkin(GameProfile gp) {
    if (getMap().containsKey(gp.getId())) {
      PropertyMap map = gp.getProperties();
      map.put("textures", getMap().get(gp.getId()));
      return;
    }
    try {
      URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + gp.getId().toString().replaceAll("-", "") + "?unsigned=false");
      


      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
      
      JSONParser parser = new JSONParser();
      JSONObject root = (JSONObject)parser.parse(reader);
      JSONArray properties = (JSONArray)root.get("properties");
      String data = (String)((JSONObject)properties.get(0)).get("value");
      
      String signature = (String)((JSONObject)properties.get(0)).get("signature");
      
      reader.close();
      PropertyMap map = gp.getProperties();
      Property property = new Property("textures", data, signature);
      map.put("textures", property);
      getMap().put(gp.getId(), property);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static String insertDashUUID(String uuid) {
	  StringBuffer sb = new StringBuffer(uuid);
	  sb.insert(8, "-");
	   
	  sb = new StringBuffer(sb.toString());
	  sb.insert(13, "-");
	   
	  sb = new StringBuffer(sb.toString());
	  sb.insert(18, "-");
	   
	  sb = new StringBuffer(sb.toString());
	  sb.insert(23, "-");
	   
	  return sb.toString();
	  }
  
  private void retrieve() {
    try {
      URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + this.name);
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
      
      JSONParser parser = new JSONParser();
      JSONObject object = (JSONObject)parser.parse(reader);
      String uid = (String)object.get("id");
      this.uuid = UUID.fromString(insertDashUUID(uid));
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static Map<UUID, Property> getMap() {
    return map;
  }
}
