package uk.co.umbaska.HologramBased;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;



public class Holograms
{
  public static HashMap<String, Hologram> holograms = new HashMap<>();
  private static final double distance = 0.23D;
  private ArrayList<String> lines = new ArrayList<>();
  private ArrayList<Integer> ids = new ArrayList<>();
  public ArrayList<String> getLines() {
	return lines;
}

public void setLines(ArrayList<String> lines) {
	this.lines = lines;
}

public boolean isShowing() {
	return showing;
}

public void setShowing(boolean showing) {
	this.showing = showing;
}

public Location getLocation() {
	return location;
}

public void setLocation(Location location) {
	this.location = location;
}

public static double getDistance() {
	return distance;
}

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

public ArrayList<Integer> getIds() {
	return ids;
}

public void setIds(ArrayList<Integer> ids) {
	this.ids = ids;
}
}
