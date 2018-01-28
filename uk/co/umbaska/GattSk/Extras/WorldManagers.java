package uk.co.umbaska.GattSk.Extras;

import ch.njol.skript.Skript;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import uk.co.umbaska.Main;

public class WorldManagers
{
  public static String error;
  public static World lastCreatedWorld;
  public static String[] worldList = new String[0];
  
  public static void createWorld(String name) {
    if (!worldExists(name)) {
      World world = Bukkit.createWorld(new WorldCreator(name));
      lastCreatedWorld = Bukkit.getWorld(world.getName());
    }
    else {
      error = "World already exists";
      worldError();
    }
  }
  
  public static void createWorldUsingGenerator(String name, String generatorName)
  {
    if (!worldExists(name)) {
      WorldCreator c = new WorldCreator(name);
      c.generator(getGenerator(generatorName, name));
      World world = c.createWorld();
      lastCreatedWorld = Bukkit.getWorld(world.getName());
    }
    else {
      error = "World already exists";
      worldError();
    }
  }
  

  static org.bukkit.generator.ChunkGenerator getGenerator(String generator, String name)
  {
    if (generator == null)
    {
      return null;
    }
    Plugin generatorPlugin = Bukkit.getPluginManager().getPlugin(generator);
    if (generatorPlugin == null)
    {
      return null;
    }
    return generatorPlugin.getDefaultWorldGenerator(name, null);
  }
  
  public static void createWorldFrom(String name, String folderName) {
    if (!worldExists(name)) {
      File srcFolder = new File(folderName);
      File destFolder = new File(name);
      if (!srcFolder.exists()) {
        error = "Folder to clone doesn't exist!";
        worldError();
        return;
      }
      
      try
      {
        FileUtils.deleteDirectory(destFolder);
        FileUtils.copyDirectory(srcFolder, destFolder);
      } catch (IOException e) {
        e.printStackTrace();
        
        System.exit(0);
      }
      

      final String worldname = name;
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
      {
        public void run() {
          File uid = new File(this.val$name + "/uid.dat");
          if (uid.exists()) {
            uid.delete();
          }
          

          World world = Bukkit.createWorld(new WorldCreator(worldname));
          WorldManagers.lastCreatedWorld = Bukkit.getWorld(world.getName()); } }, 60L);



    }
    else
    {


      error = "World already exists";
      worldError();
    }
  }
  

  public static void copyFolder(File src, File dest)
    throws IOException
  {
    if (src.isDirectory())
    {

      if (!dest.exists()) {
        dest.mkdir();
        System.out.println("Directory copied from " + src + "  to " + dest);
      }
      


      String[] files = src.list();
      
      for (String file : files)
      {
        File srcFile = new File(src, file);
        File destFile = new File(dest, file);
        
        copyFolder(srcFile, destFile);
      }
      
    }
    else
    {
      InputStream in = new FileInputStream(src);
      OutputStream out = new FileOutputStream(dest);
      
      byte[] buffer = new byte['Ѐ'];
      
      int length;
      
      while ((length = in.read(buffer)) > 0) {
        out.write(buffer, 0, length);
      }
      
      in.close();
      out.close();
      System.out.println("File copied from " + src + " to " + dest);
    }
  }
  
  public static void deleteWorld(String name) { if (worldExists(name)) {
      Bukkit.unloadWorld(name, false);
      
      String delfile = name.replaceAll("/", java.util.regex.Matcher.quoteReplacement(File.separator));
      
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
      {
        public void run() {
          File file = new File(this.val$delfile);
          file.delete(); } }, 20L);


    }
    else
    {

      error = "World doesn't exist";
      worldError();
    }
  }
  
  public static void unloadWorld(String name) {
    if (worldExists(name)) {
      Bukkit.unloadWorld(name, true);
    }
    else {
      error = "World doesn't exist";
      worldError();
    }
  }
  
  public static void loadWorld(String name) {
    if (!worldExists(name)) {
      Bukkit.createWorld(new WorldCreator(name));
    }
    else {
      error = "World already exists";
      worldError();
    }
  }
  
  public static boolean worldExists(String name)
  {
    if (Bukkit.getWorld(name) != null) {
      return true;
    }
    
    return false;
  }
  
  public static void worldError()
  {
    Skript.error(Skript.SKRIPT_PREFIX + "Error occured within WorldManager'" + error + "'");
  }
}
