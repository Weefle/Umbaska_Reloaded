package uk.co.umbaska.LargeSk.register;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.konsolas.aac.api.HackType;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import uk.co.umbaska.LargeSk.bungee.EvtPluginMessageReceived;
import uk.co.umbaska.LargeSk.bungee.LargeMessenger;
import uk.co.umbaska.LargeSk.util.EnumClassInfo;
import uk.co.umbaska.Umbaska;

public class LargeSkRegister
{
  public void registerAll()
  {
    Expressions expressions = new Expressions();
    Events events = new Events();
    
    expressions.registerGeneral();
    events.registerGeneral();
    if (Bukkit.getServer().getPluginManager().getPlugin("AAC") != null)
    {
      expressions.registerAAC();
      events.registerAAC();
      EnumClassInfo.create(HackType.class, "hacktype").register();
    }
    if (!Umbaska.isUsingBungee) {
      return;
    }
    new LargeMessenger().registerMessenger();
    
    Skript.registerEvent("Message Receive", SimpleEvent.class, EvtPluginMessageReceived.class, new String[] { "message [(receiv(e|ing)|get[ting])]" });
    
    EventValues.registerEventValue(EvtPluginMessageReceived.class, String.class, new Getter()
    {
      public String get(EvtPluginMessageReceived event)
      {
        return event.getMessage();
      }
    }, 0);
  }
}
