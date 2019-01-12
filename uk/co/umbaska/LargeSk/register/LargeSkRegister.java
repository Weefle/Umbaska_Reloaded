package uk.co.umbaska.LargeSk.register;

import org.bukkit.Bukkit;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.konsolas.aac.api.HackType;
import uk.co.umbaska.Main;
import uk.co.umbaska.LargeSk.bungee.EvtPluginMessageReceived;
import uk.co.umbaska.LargeSk.bungee.LargeMessenger;
import uk.co.umbaska.LargeSk.util.EnumClassInfo;

public class LargeSkRegister
{
  @SuppressWarnings("unchecked")
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
    if (!Main.use_bungee) {
      return;
    }
    new LargeMessenger().registerMessenger();
    
    Skript.registerEvent("Message Receive", SimpleEvent.class, EvtPluginMessageReceived.class, new String[] { "message [(receiv(e|ing)|get[ting])]" });
    
    EventValues.registerEventValue(EvtPluginMessageReceived.class, String.class, new Getter<String, EvtPluginMessageReceived>()
    {
      public String get(EvtPluginMessageReceived event)
      {
        return event.getMessage();
      }
    }, 0);
  }
}
