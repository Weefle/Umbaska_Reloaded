package uk.co.umbaska.HologramBased;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;



public class Holograms
{
  public static HashMap<String, Hologram> holograms = new HashMap();
  private static final double distance = 0.23D;
  private ArrayList<String> lines = new ArrayList();
  private ArrayList<Integer> ids = new ArrayList();
  private boolean showing = false;
  private Location location;
  
  public static Hologram get(String id)
  {
    if (holograms.containsKey(id)) {
      return (Hologram)holograms.get(id);
    }
    
    Location loc = new Location((World)Bukkit.getServer().getWorlds().get(0), 0.0D, 0.0D, 0.0D);
    Hologram h = new Hologram(loc, new String[] { "Default Text" }).start();
    holograms.put(id, h);
    return h;
  }
}
