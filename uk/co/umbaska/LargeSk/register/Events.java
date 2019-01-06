package uk.co.umbaska.LargeSk.register;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import java.util.ArrayList;
import java.util.logging.Logger;
import me.konsolas.aac.api.HackType;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import uk.co.umbaska.LargeSk.events.EvtConsoleLog;
import uk.co.umbaska.LargeSk.events.EvtPlayerChunkChange;
import uk.co.umbaska.LargeSk.events.EvtPlayerViolation;
import uk.co.umbaska.LargeSk.events.PlayerChunkChangeEvt;
import uk.co.umbaska.LargeSk.events.PlayerViolationEvt;
import uk.co.umbaska.LargeSk.util.ConsoleFilter;
import uk.co.umbaska.Umbaska;

public class Events
{
  public void registerGeneral()
  {
    ArrayList<Logger> loggers = new ArrayList();
    loggers.add(Bukkit.getLogger());
    for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
      loggers.add(pl.getLogger());
    }
    for (Logger lg : loggers) {
      lg.setFilter(new ConsoleFilter());
    }
    Skript.registerEvent("console log", SimpleEvent.class, EvtConsoleLog.class, new String[] { "[console] log[ging]" });
    
    EventValues.registerEventValue(EvtConsoleLog.class, String.class, new Getter()
    {
      public String get(EvtConsoleLog event)
      {
        return event.getMessage();
      }
    }, 0);
    
    Bukkit.getServer().getPluginManager().registerEvents(new PlayerChunkChangeEvt(), Umbaska.get());
    
    Skript.registerEvent("chunk change", SimpleEvent.class, EvtPlayerChunkChange.class, new String[] { "chunk change" });
    
    EventValues.registerEventValue(EvtPlayerChunkChange.class, Player.class, new Getter()
    {
      public Player get(EvtPlayerChunkChange event)
      {
        return event.getPlayer();
      }
    }, 0);
    
    EventValues.registerEventValue(EvtPlayerChunkChange.class, Chunk.class, new Getter()
    {
      public Chunk get(EvtPlayerChunkChange event)
      {
        return event.getFrom();
      }
    }, 0);
    
    EventValues.registerEventValue(EvtPlayerChunkChange.class, Chunk.class, new Getter()
    {
      public Chunk get(EvtPlayerChunkChange event)
      {
        return event.getTo();
      }
    }, 0);
  }
  
  public void registerAAC()
  {
    Bukkit.getServer().getPluginManager().registerEvents(new PlayerViolationEvt(), Umbaska.get());
    
    Skript.registerEvent("Player Violation", SimpleEvent.class, EvtPlayerViolation.class, new String[] { "violation", "hack", "cheat" });
    
    EventValues.registerEventValue(EvtPlayerViolation.class, Player.class, new Getter()
    {
      public Player get(EvtPlayerViolation event)
      {
        return event.getPlayer();
      }
    }, 0);
    
    EventValues.registerEventValue(EvtPlayerViolation.class, HackType.class, new Getter()
    {
      public HackType get(EvtPlayerViolation event)
      {
        return event.getHack();
      }
    }, 0);
    
    EventValues.registerEventValue(EvtPlayerViolation.class, String.class, new Getter()
    {
      public String get(EvtPlayerViolation event)
      {
        return event.getMessage();
      }
    }, 0);
    
    EventValues.registerEventValue(EvtPlayerViolation.class, Integer.class, new Getter()
    {
      public Integer get(EvtPlayerViolation event)
      {
        return event.getViolations();
      }
    }, 0);
  }
}
