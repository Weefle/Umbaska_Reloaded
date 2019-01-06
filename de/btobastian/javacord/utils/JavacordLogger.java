package de.btobastian.javacord.utils;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

public class JavacordLogger
  extends MarkerIgnoringBase
{
  private final String name;
  
  public JavacordLogger(String name)
  {
    this.name = name;
    
    Logger.getLogger(name).setLevel(Level.ALL);
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public final boolean isTraceEnabled()
  {
    return false;
  }
  
  public final void trace(String msg) {}
  
  public final void trace(String format, Object arg) {}
  
  public final void trace(String format, Object arg1, Object arg2) {}
  
  public final void trace(String format, Object... arguments) {}
  
  public final void trace(String msg, Throwable t) {}
  
  public final boolean isDebugEnabled()
  {
    return LoggerUtil.isDebug();
  }
  
  public final void debug(String msg)
  {
    if (isDebugEnabled())
    {
      LogRecord record = new LogRecord(Level.FINE, msg);
      record.setLoggerName(this.name);
      Logger.getLogger(this.name).log(record);
    }
  }
  
  public final void debug(String format, Object arg1)
  {
    if (isDebugEnabled())
    {
      FormattingTuple ft = MessageFormatter.format(format, arg1);
      LogRecord record = new LogRecord(Level.FINE, ft.getMessage());
      record.setThrown(ft.getThrowable());
      record.setLoggerName(this.name);
      Logger.getLogger(this.name).log(record);
    }
  }
  
  public final void debug(String format, Object arg1, Object arg2)
  {
    if (isDebugEnabled())
    {
      FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
      LogRecord record = new LogRecord(Level.FINE, ft.getMessage());
      record.setThrown(ft.getThrowable());
      record.setLoggerName(this.name);
      Logger.getLogger(this.name).log(record);
    }
  }
  
  public final void debug(String format, Object... arguments)
  {
    if (isDebugEnabled())
    {
      FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
      LogRecord record = new LogRecord(Level.FINE, ft.getMessage());
      record.setThrown(ft.getThrowable());
      record.setLoggerName(this.name);
      Logger.getLogger(this.name).log(record);
    }
  }
  
  public final void debug(String msg, Throwable t)
  {
    if (isDebugEnabled())
    {
      LogRecord record = new LogRecord(Level.FINE, msg);
      record.setThrown(t);
      record.setLoggerName(this.name);
      Logger.getLogger(this.name).log(record);
    }
  }
  
  public final boolean isInfoEnabled()
  {
    return true;
  }
  
  public final void info(String msg)
  {
    LogRecord record = new LogRecord(Level.INFO, msg);
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void info(String format, Object arg1)
  {
    FormattingTuple ft = MessageFormatter.format(format, arg1);
    LogRecord record = new LogRecord(Level.INFO, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void info(String format, Object arg1, Object arg2)
  {
    FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    LogRecord record = new LogRecord(Level.INFO, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void info(String format, Object... arguments)
  {
    FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    LogRecord record = new LogRecord(Level.INFO, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void info(String msg, Throwable t)
  {
    LogRecord record = new LogRecord(Level.INFO, msg);
    record.setThrown(t);
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final boolean isWarnEnabled()
  {
    return true;
  }
  
  public final void warn(String msg)
  {
    LogRecord record = new LogRecord(Level.WARNING, msg);
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void warn(String format, Object arg1)
  {
    FormattingTuple ft = MessageFormatter.format(format, arg1);
    LogRecord record = new LogRecord(Level.WARNING, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void warn(String format, Object arg1, Object arg2)
  {
    FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    LogRecord record = new LogRecord(Level.WARNING, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void warn(String format, Object... arguments)
  {
    FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    LogRecord record = new LogRecord(Level.WARNING, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void warn(String msg, Throwable t)
  {
    LogRecord record = new LogRecord(Level.WARNING, msg);
    record.setThrown(t);
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final boolean isErrorEnabled()
  {
    return true;
  }
  
  public final void error(String msg)
  {
    LogRecord record = new LogRecord(Level.SEVERE, msg);
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void error(String format, Object arg1)
  {
    FormattingTuple ft = MessageFormatter.format(format, arg1);
    LogRecord record = new LogRecord(Level.SEVERE, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void error(String format, Object arg1, Object arg2)
  {
    FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    LogRecord record = new LogRecord(Level.SEVERE, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void error(String format, Object... arguments)
  {
    FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    LogRecord record = new LogRecord(Level.SEVERE, ft.getMessage());
    record.setThrown(ft.getThrowable());
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
  
  public final void error(String msg, Throwable t)
  {
    LogRecord record = new LogRecord(Level.SEVERE, msg);
    record.setThrown(t);
    record.setLoggerName(this.name);
    Logger.getLogger(this.name).log(record);
  }
}
