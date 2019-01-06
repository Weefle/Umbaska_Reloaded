package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.event.SubstituteLoggingEvent;

public class SubstituteLoggerFactory
  implements ILoggerFactory
{
  final ConcurrentMap<String, SubstituteLogger> loggers = new ConcurrentHashMap();
  final List<SubstituteLoggingEvent> eventList = Collections.synchronizedList(new ArrayList());
  
  public Logger getLogger(String name)
  {
    SubstituteLogger logger = (SubstituteLogger)this.loggers.get(name);
    if (logger == null)
    {
      logger = new SubstituteLogger(name, this.eventList);
      SubstituteLogger oldLogger = (SubstituteLogger)this.loggers.putIfAbsent(name, logger);
      if (oldLogger != null) {
        logger = oldLogger;
      }
    }
    return logger;
  }
  
  public List<String> getLoggerNames()
  {
    return new ArrayList(this.loggers.keySet());
  }
  
  public List<SubstituteLogger> getLoggers()
  {
    return new ArrayList(this.loggers.values());
  }
  
  public List<SubstituteLoggingEvent> getEventList()
  {
    return this.eventList;
  }
  
  public void clear()
  {
    this.loggers.clear();
    this.eventList.clear();
  }
}
