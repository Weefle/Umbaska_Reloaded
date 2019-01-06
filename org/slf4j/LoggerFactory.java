package org.slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.helpers.SubstituteLogger;
import org.slf4j.helpers.SubstituteLoggerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticLoggerBinder;

public final class LoggerFactory
{
  static final String CODES_PREFIX = "http://www.slf4j.org/codes.html";
  static final String NO_STATICLOGGERBINDER_URL = "http://www.slf4j.org/codes.html#StaticLoggerBinder";
  static final String MULTIPLE_BINDINGS_URL = "http://www.slf4j.org/codes.html#multiple_bindings";
  static final String NULL_LF_URL = "http://www.slf4j.org/codes.html#null_LF";
  static final String VERSION_MISMATCH = "http://www.slf4j.org/codes.html#version_mismatch";
  static final String SUBSTITUTE_LOGGER_URL = "http://www.slf4j.org/codes.html#substituteLogger";
  static final String LOGGER_NAME_MISMATCH_URL = "http://www.slf4j.org/codes.html#loggerNameMismatch";
  static final String REPLAY_URL = "http://www.slf4j.org/codes.html#replay";
  static final String UNSUCCESSFUL_INIT_URL = "http://www.slf4j.org/codes.html#unsuccessfulInit";
  static final String UNSUCCESSFUL_INIT_MSG = "org.slf4j.LoggerFactory could not be successfully initialized. See also http://www.slf4j.org/codes.html#unsuccessfulInit";
  static final int UNINITIALIZED = 0;
  static final int ONGOING_INITIALIZATION = 1;
  static final int FAILED_INITIALIZATION = 2;
  static final int SUCCESSFUL_INITIALIZATION = 3;
  static final int NOP_FALLBACK_INITIALIZATION = 4;
  static int INITIALIZATION_STATE = 0;
  static SubstituteLoggerFactory SUBST_FACTORY = new SubstituteLoggerFactory();
  static NOPLoggerFactory NOP_FALLBACK_FACTORY = new NOPLoggerFactory();
  static final String DETECT_LOGGER_NAME_MISMATCH_PROPERTY = "slf4j.detectLoggerNameMismatch";
  static final String JAVA_VENDOR_PROPERTY = "java.vendor.url";
  static boolean DETECT_LOGGER_NAME_MISMATCH = Util.safeGetBooleanSystemProperty("slf4j.detectLoggerNameMismatch");
  private static final String[] API_COMPATIBILITY_LIST = { "1.6", "1.7" };
  
  static void reset()
  {
    INITIALIZATION_STATE = 0;
  }
  
  private static final void performInitialization()
  {
    
    if (INITIALIZATION_STATE == 3) {
      versionSanityCheck();
    }
  }
  
  private static boolean messageContainsOrgSlf4jImplStaticLoggerBinder(String msg)
  {
    if (msg == null) {
      return false;
    }
    if (msg.contains("org/slf4j/impl/StaticLoggerBinder")) {
      return true;
    }
    if (msg.contains("org.slf4j.impl.StaticLoggerBinder")) {
      return true;
    }
    return false;
  }
  
  private static final void bind()
  {
    try
    {
      Set<URL> staticLoggerBinderPathSet = null;
      if (!isAndroid())
      {
        staticLoggerBinderPathSet = findPossibleStaticLoggerBinderPathSet();
        reportMultipleBindingAmbiguity(staticLoggerBinderPathSet);
      }
      StaticLoggerBinder.getSingleton();
      INITIALIZATION_STATE = 3;
      reportActualBinding(staticLoggerBinderPathSet);
      fixSubstitutedLoggers();
      playRecordedEvents();
      SUBST_FACTORY.clear();
    }
    catch (NoClassDefFoundError ncde)
    {
      String msg = ncde.getMessage();
      if (messageContainsOrgSlf4jImplStaticLoggerBinder(msg))
      {
        INITIALIZATION_STATE = 4;
        Util.report("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
        Util.report("Defaulting to no-operation (NOP) logger implementation");
        Util.report("See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.");
      }
      else
      {
        failedBinding(ncde);
        throw ncde;
      }
    }
    catch (NoSuchMethodError nsme)
    {
      String msg = nsme.getMessage();
      if ((msg != null) && (msg.contains("org.slf4j.impl.StaticLoggerBinder.getSingleton()")))
      {
        INITIALIZATION_STATE = 2;
        Util.report("slf4j-api 1.6.x (or later) is incompatible with this binding.");
        Util.report("Your binding is version 1.5.5 or earlier.");
        Util.report("Upgrade your binding to version 1.6.x.");
      }
      throw nsme;
    }
    catch (Exception e)
    {
      failedBinding(e);
      throw new IllegalStateException("Unexpected initialization failure", e);
    }
  }
  
  static void failedBinding(Throwable t)
  {
    INITIALIZATION_STATE = 2;
    Util.report("Failed to instantiate SLF4J LoggerFactory", t);
  }
  
  private static void playRecordedEvents()
  {
    List<SubstituteLoggingEvent> events = SUBST_FACTORY.getEventList();
    if (events.isEmpty()) {
      return;
    }
    for (int i = 0; i < events.size(); i++)
    {
      SubstituteLoggingEvent event = (SubstituteLoggingEvent)events.get(i);
      SubstituteLogger substLogger = event.getLogger();
      if (substLogger.isDelegateNOP()) {
        break;
      }
      if (substLogger.isDelegateEventAware())
      {
        if (i == 0) {
          emitReplayWarning(events.size());
        }
        substLogger.log(event);
      }
      else
      {
        if (i == 0) {
          emitSubstitutionWarning();
        }
        Util.report(substLogger.getName());
      }
    }
  }
  
  private static final void fixSubstitutedLoggers()
  {
    List<SubstituteLogger> loggers = SUBST_FACTORY.getLoggers();
    if (loggers.isEmpty()) {
      return;
    }
    for (SubstituteLogger subLogger : loggers)
    {
      Logger logger = getLogger(subLogger.getName());
      subLogger.setDelegate(logger);
    }
  }
  
  private static void emitSubstitutionWarning()
  {
    Util.report("The following set of substitute loggers may have been accessed");
    Util.report("during the initialization phase. Logging calls during this");
    Util.report("phase were not honored. However, subsequent logging calls to these");
    Util.report("loggers will work as normally expected.");
    Util.report("See also http://www.slf4j.org/codes.html#substituteLogger");
  }
  
  private static void emitReplayWarning(int eventCount)
  {
    Util.report("A number (" + eventCount + ") of logging calls during the initialization phase have been intercepted and are");
    Util.report("now being replayed. These are suject to the filtering rules of the underlying logging system.");
    Util.report("See also http://www.slf4j.org/codes.html#replay");
  }
  
  private static final void versionSanityCheck()
  {
    try
    {
      String requested = StaticLoggerBinder.REQUESTED_API_VERSION;
      
      boolean match = false;
      for (String aAPI_COMPATIBILITY_LIST : API_COMPATIBILITY_LIST) {
        if (requested.startsWith(aAPI_COMPATIBILITY_LIST)) {
          match = true;
        }
      }
      if (!match)
      {
        Util.report("The requested version " + requested + " by your slf4j binding is not compatible with " + Arrays.asList(API_COMPATIBILITY_LIST).toString());
        
        Util.report("See http://www.slf4j.org/codes.html#version_mismatch for further details.");
      }
    }
    catch (NoSuchFieldError nsfe) {}catch (Throwable e)
    {
      Util.report("Unexpected problem occured during version sanity check", e);
    }
  }
  
  private static String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";
  
  static Set<URL> findPossibleStaticLoggerBinderPathSet()
  {
    Set<URL> staticLoggerBinderPathSet = new LinkedHashSet();
    try
    {
      ClassLoader loggerFactoryClassLoader = LoggerFactory.class.getClassLoader();
      Enumeration<URL> paths;
      Enumeration<URL> paths;
      if (loggerFactoryClassLoader == null) {
        paths = ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
      } else {
        paths = loggerFactoryClassLoader.getResources(STATIC_LOGGER_BINDER_PATH);
      }
      while (paths.hasMoreElements())
      {
        URL path = (URL)paths.nextElement();
        staticLoggerBinderPathSet.add(path);
      }
    }
    catch (IOException ioe)
    {
      Util.report("Error getting resources from path", ioe);
    }
    return staticLoggerBinderPathSet;
  }
  
  private static boolean isAmbiguousStaticLoggerBinderPathSet(Set<URL> binderPathSet)
  {
    return binderPathSet.size() > 1;
  }
  
  private static void reportMultipleBindingAmbiguity(Set<URL> binderPathSet)
  {
    if (isAmbiguousStaticLoggerBinderPathSet(binderPathSet))
    {
      Util.report("Class path contains multiple SLF4J bindings.");
      for (URL path : binderPathSet) {
        Util.report("Found binding in [" + path + "]");
      }
      Util.report("See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.");
    }
  }
  
  private static boolean isAndroid()
  {
    String vendor = Util.safeGetSystemProperty("java.vendor.url");
    if (vendor == null) {
      return false;
    }
    return vendor.toLowerCase().contains("android");
  }
  
  private static void reportActualBinding(Set<URL> binderPathSet)
  {
    if ((binderPathSet != null) && (isAmbiguousStaticLoggerBinderPathSet(binderPathSet))) {
      Util.report("Actual binding is of type [" + StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr() + "]");
    }
  }
  
  public static Logger getLogger(String name)
  {
    ILoggerFactory iLoggerFactory = getILoggerFactory();
    return iLoggerFactory.getLogger(name);
  }
  
  public static Logger getLogger(Class<?> clazz)
  {
    Logger logger = getLogger(clazz.getName());
    if (DETECT_LOGGER_NAME_MISMATCH)
    {
      Class<?> autoComputedCallingClass = Util.getCallingClass();
      if ((autoComputedCallingClass != null) && (nonMatchingClasses(clazz, autoComputedCallingClass)))
      {
        Util.report(String.format("Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", new Object[] { logger.getName(), autoComputedCallingClass.getName() }));
        
        Util.report("See http://www.slf4j.org/codes.html#loggerNameMismatch for an explanation");
      }
    }
    return logger;
  }
  
  private static boolean nonMatchingClasses(Class<?> clazz, Class<?> autoComputedCallingClass)
  {
    return !autoComputedCallingClass.isAssignableFrom(clazz);
  }
  
  public static ILoggerFactory getILoggerFactory()
  {
    if (INITIALIZATION_STATE == 0) {
      synchronized (LoggerFactory.class)
      {
        if (INITIALIZATION_STATE == 0)
        {
          INITIALIZATION_STATE = 1;
          performInitialization();
        }
      }
    }
    switch (INITIALIZATION_STATE)
    {
    case 3: 
      return StaticLoggerBinder.getSingleton().getLoggerFactory();
    case 4: 
      return NOP_FALLBACK_FACTORY;
    case 2: 
      throw new IllegalStateException("org.slf4j.LoggerFactory could not be successfully initialized. See also http://www.slf4j.org/codes.html#unsuccessfulInit");
    case 1: 
      return SUBST_FACTORY;
    }
    throw new IllegalStateException("Unreachable code");
  }
}
