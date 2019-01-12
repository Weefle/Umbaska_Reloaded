package uk.co.umbaska.LargeSk.util;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

import org.bukkit.Bukkit;

import uk.co.umbaska.LargeSk.events.EvtConsoleLog;

public final class ConsoleFilter
  implements Filter
{
  public boolean isLoggable(LogRecord record)
  {
    EvtConsoleLog evt = new EvtConsoleLog(record.getMessage());
    Bukkit.getServer().getPluginManager().callEvent(evt);
    return !evt.isCancelled();
  }
}
