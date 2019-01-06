package uk.co.umbaska;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.ChatColor;

public class UpdateChecker
{
  int delay;
  Plugin plugin;
  String currentVersion;
  BukkitTask task;
  
  public UpdateChecker(Plugin p, Integer minutesBetweenChecks)
  {
    this.plugin = p;
    this.delay = minutesBetweenChecks.intValue();
    this.currentVersion = p.getDescription().getVersion();
  }
  
  private static synchronized String readAll(Reader rd) throws IOException
  {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char)cp);
    }
    return sb.toString();
  }
  
  public synchronized void runNow() {
    String version = this.currentVersion;
    try {
      String url = "https://pastebin.com/raw/i20BnAxd";
      InputStream is = new URL(url).openStream();
      BufferedReader rd = new BufferedReader(new java.io.InputStreamReader(is, Charset.defaultCharset()));
      version = readAll(rd);
      is.close();
    } catch (IOException e) {
      Bukkit.getLogger().warning("Oh no! Couldn't get new version. Oopsidaisi!");
    }
    if (!this.currentVersion.equalsIgnoreCase(version)) {
      for (int time = 0; time < 6; time++) {
        Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', "&3&lUmbaska 4.0.0 is out of date! The newest version is " + version + "! Please download the new version from https://github.com/Weefle/Umbaska_Reloaded/releases !"));
      }
    }
  }
  
  public synchronized UpdateChecker start() {
    this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, new Runnable()
    {

      public void run() { UpdateChecker.this.runNow(); } }, this.delay * 60 * 20, this.delay * 60 * 20);
    

    return this;
  }
}
