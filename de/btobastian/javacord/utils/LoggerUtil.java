package de.btobastian.javacord.utils;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil
{
  private static volatile boolean initialized = false;
  private static final Object initLock = new Object();
  private static final HashMap<String, Logger> loggers = new HashMap();
  private static volatile boolean noLogger = false;
  private static volatile boolean debug = false;
  
  public static Logger getLogger(String name)
  {
    synchronized (initLock)
    {
      if (!initialized) {
        init();
      }
    }
    if (noLogger) {
      synchronized (loggers)
      {
        Object logger = (Logger)loggers.get(name);
        if (logger == null)
        {
          logger = new JavacordLogger(name);
          loggers.put(name, logger);
        }
        return (Logger)logger;
      }
    }
    return LoggerFactory.getLogger(name);
  }
  
  public static Logger getLogger(Class clazz)
  {
    return getLogger(clazz.getName());
  }
  
  public static void setDebug(boolean debug)
  {
    debug = debug;
  }
  
  public static boolean isDebug()
  {
    return debug;
  }
  
  private static void init()
  {
    initialized = true;
    try
    {
      Class.forName("org.slf4j.impl.StaticLoggerBinder");
    }
    catch (ClassNotFoundException e)
    {
      noLogger = true;
      getLogger(LoggerUtil.class).info("No SLF4J compatible logger was found. Using default javacord implementation!");
    }
  }
}
