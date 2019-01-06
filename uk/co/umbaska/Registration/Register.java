package uk.co.umbaska.Registration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.reflections.Reflections;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;
import uk.co.umbaska.Umbaska;
import uk.co.umbaska.LargeSk.register.LargeSkRegister;
import uk.co.umbaska.ProtocolLib.EntityHider;
import uk.co.umbaska.Utils.DocsPassword;
import uk.co.umbaska.Utils.UmbaskaLogLevel;

public class Register
{
  private static boolean generateDocs;
  private static ArrayList<String> docList = new ArrayList();
  private static PluginManager pluginManager = Bukkit.getPluginManager();
  private static Plugin plugin;
  private static HashMap<SyntaxElement, Integer> registeredAmount = new HashMap();
  private static HashMap<SyntaxElement, Integer> failedAmount = new HashMap();
  
  public static void registerAll()
  {
    registerAll("uk.co.umbaska");
  }
  
  public static void registerAll(String pckg)
  {
    long startTime = System.nanoTime();
    generateDocs = Umbaska.debug;
    
    PluginManager pm = Bukkit.getPluginManager();
    Plugin plotMe = pm.getPlugin("PlotMe");
    Plugin plotSquared = pm.getPlugin("PlotSquared");
    String plotMessagePrefix = "";
    if (plotMe != null)
    {
      Umbaska.logWarning(new Object[] { "Umbaska supports only an old version of PlotMe - 0.13b!" });
      Umbaska.logWarning(new Object[] { "Consider migrating to PlotSquared - it's better in general and has a good Umbaska support" });
      plotMessagePrefix = "Also, ";
    }
    if (((plotMe != null ? 1 : 0) & (plotSquared != null ? 1 : 0)) != 0)
    {
      Umbaska.logFail(new Object[] { plotMessagePrefix, "Umbaska DOESN'T support having both PlotSquared and PlotMe installed on one server." });
      
      Umbaska.logFail(new Object[] { "There is also no need for you to use both. Consider removing one." });
    }
    new LargeSkRegister().registerAll();
    if (pluginManager.getPlugin("ProtocolLib") != null) {
      Umbaska.protocolLibEntityHider = new EntityHider(Umbaska.get(), EntityHider.Policy.BLACKLIST);
    }
    Reflections refls = new Reflections(pckg, new Scanner[0]);
    for (SyntaxElement element : SyntaxElement.values()) {
      registeredAmount.put(element, Integer.valueOf(0));
    }
    for (SyntaxElement element : SyntaxElement.values()) {
      failedAmount.put(element, Integer.valueOf(0));
    }
    Set<Class<?>> infoClasses = refls.getTypesAnnotatedWith(InfoClass.class);
    for (Iterator i$ = SyntaxElement.getUmbaskaExtensionClasses().iterator(); i$.hasNext();)
    {
      exClass = (Class)i$.next();
      Umbaska.logEmpty(UmbaskaLogLevel.DEBUG);
      Umbaska.logDebug(new Object[] { "Finding Version-Dependent ", exClass.getName() });
      Set<Class<?>> classes = refls.getSubTypesOf(exClass);
      for (Class clazz : classes)
      {
        String name = clazz.getName();
        String dashV = name.substring(name.length() - 8, name.length() - 6);
        if (dashV.equalsIgnoreCase("_V"))
        {
          Version ver = Version.parse(name.substring(name.length() - 7));
          Umbaska.logDebug(clazz, new Object[] { "Found compatibility with Minecraft ", ver, ", UmbVerID: ", Integer.valueOf(ver.getId()), Version.serverVersion == ver ? ". Registering..." : null });
          if (Version.serverVersion == ver)
          {
            String elementName = name.substring(0, name.length() - 8);
            Class info = null;
            for (Class cla : infoClasses) {
              if (cla.getName().substring(0, cla.getName().length() - 5).equalsIgnoreCase(elementName)) {
                info = cla;
              }
            }
            if (info == null)
            {
              Umbaska.logFail(clazz, new Object[] { "I couldn't find any matching class ending with \"_Info\"." });
            }
            else
            {
              SyntaxElement element = SyntaxElement.parseClass(exClass);
              if (element == SyntaxElement.CONDITION) {
                condition(clazz, info);
              } else if (element == SyntaxElement.EFFECT) {
                effect(clazz, info);
              } else if (element == SyntaxElement.SIMPLE_EXPRESSION) {
                simpleExpression(clazz, info);
              } else if (element == SyntaxElement.SIMPLE_PROPERTY_EXPRESSION) {
                simplePropertyExpression(clazz, info);
              } else if (element == SyntaxElement.PROPERTY_EXPRESSION) {
                propertyExpression(clazz, info);
              } else {
                Umbaska.logError(clazz, new Object[] { "Unknown SyntaxElement.. something is wrong with the enum." });
              }
            }
          }
        }
      }
    }
    Class exClass;
    Umbaska.logDebug(new Object[] { "Registrating Conditions.." });
    Umbaska.logEmpty(UmbaskaLogLevel.DEBUG);
    Set<Class<? extends Condition>> conditions = refls.getSubTypesOf(Condition.class);
    for (Class<? extends Condition> clazz : conditions)
    {
      Umbaska.logDebug(clazz, new Object[] { "Starting Registration for a Condition." });
      condition(clazz, clazz);
    }
    Umbaska.logEmpty(UmbaskaLogLevel.DEBUG, 2);
    
    Umbaska.logDebug(new Object[] { "Registrating Effects.." });
    Set<Class<? extends Effect>> effects = refls.getSubTypesOf(Effect.class);
    for (Class<? extends Effect> clazz : effects)
    {
      Umbaska.logDebug(clazz, new Object[] { "Starting Registration for an Effect" });
      effect(clazz, clazz);
    }
    Umbaska.logEmpty(UmbaskaLogLevel.DEBUG, 2);
    
    Umbaska.logDebug(new Object[] { "Registrating SimpleExpressions.." });
    Umbaska.logEmpty(UmbaskaLogLevel.DEBUG);
    Set<Class<? extends SimpleExpression>> simpleExpressions = refls.getSubTypesOf(SimpleExpression.class);
    for (Class<? extends SimpleExpression> clazz : simpleExpressions)
    {
      Umbaska.logDebug(clazz, new Object[] { "Starting Registration for a SimpleExpression." });
      simpleExpression(clazz, clazz);
    }
    Umbaska.logEmpty(UmbaskaLogLevel.DEBUG, 2);
    
    Umbaska.logDebug(new Object[] { "Registrating SimplePropertyExpressions.." });
    Umbaska.logEmpty(UmbaskaLogLevel.DEBUG);
    Set<Class<? extends SimplePropertyExpression>> simplePropertyExpressions = refls.getSubTypesOf(SimplePropertyExpression.class);
    for (Class<? extends SimplePropertyExpression> clazz : simplePropertyExpressions)
    {
      Umbaska.logDebug(clazz, new Object[] { "Starting Registration for a SimplePropertyExpression." });
      simplePropertyExpression(clazz, clazz);
    }
    Umbaska.logDebug(new Object[] { "Registrating PropertyExpressions.." });
    Umbaska.logEmpty(UmbaskaLogLevel.DEBUG);
    Set<Class<? extends PropertyExpression>> propertyExpressions = refls.getSubTypesOf(PropertyExpression.class);
    for (Class<? extends PropertyExpression> clazz : propertyExpressions)
    {
      Umbaska.logDebug(clazz, new Object[] { "Starting Registration for a PropertyExpression." });
      propertyExpression(clazz, clazz);
    }
    Umbaska.logEmpty(UmbaskaLogLevel.DEBUG, 2);
    
    DecimalFormat df = new DecimalFormat();
    df.setMinimumFractionDigits(1);
    df.setMaximumFractionDigits(1);
    int totalRegisteredAmount = 0;
    int totalFailedAmount = 0;
    for (Iterator i$ = registeredAmount.values().iterator(); i$.hasNext();)
    {
      int loopAmount = ((Integer)i$.next()).intValue();
      totalRegisteredAmount += loopAmount;
    }
    for (Iterator i$ = failedAmount.values().iterator(); i$.hasNext();)
    {
      int loopAmount = ((Integer)i$.next()).intValue();
      totalFailedAmount += loopAmount;
    }
    int totalAmount = totalFailedAmount + totalRegisteredAmount;
    double percentage = totalFailedAmount == 0 ? 100.0D : totalRegisteredAmount / totalAmount * 100.0D;
    
    String formattedPercentage = df.format(percentage);
    long took = System.nanoTime() - startTime;
    double tookMs = took / 1000000.0D;
    String tookString = df.format(tookMs);
    Umbaska.log(new Object[] { "Registered ", Integer.valueOf(totalRegisteredAmount), " out of ", Integer.valueOf(totalRegisteredAmount + totalFailedAmount), " elements with ", Integer.valueOf(totalFailedAmount), " errors (", formattedPercentage, "%) - Took ", tookString, "ms." });
  }
  
  public static void condition(Class<? extends Condition> clazz, Class<? extends Condition> info)
  {
    Syntaxes syntaxes = validateAndGenerateDocs(SyntaxElement.CONDITION, clazz, info);
    if (syntaxes == null) {
      return;
    }
    Skript.registerCondition(clazz, syntaxes.value());
  }
  
  public static void effect(Class<? extends Effect> clazz, Class<? extends Effect> info)
  {
    Syntaxes syntaxes = validateAndGenerateDocs(SyntaxElement.EFFECT, clazz, info);
    if (syntaxes == null) {
      return;
    }
    Skript.registerEffect(clazz, syntaxes.value());
  }
  
  public static void simpleExpression(Class<? extends SimpleExpression> clazz, Class<? extends SimpleExpression> info)
  {
    Syntaxes syntaxes = validateAndGenerateDocs(SyntaxElement.SIMPLE_EXPRESSION, clazz, info);
    if (syntaxes == null) {
      return;
    }
    Class returnClazz = getExpressionReturnType(clazz);
    if (returnClazz == null) {
      Umbaska.logFail(Umbaska.debug ? null : clazz, new Object[] { "Couldn't register this SimpleExpression because the returnClazz is null" });
    }
    Skript.registerExpression(clazz, returnClazz, ExpressionType.SIMPLE, syntaxes.value());
  }
  
  public static void simplePropertyExpression(Class<? extends SimplePropertyExpression> clazz, Class<? extends SimplePropertyExpression> info)
  {
    Syntaxes syntaxes = validateAndGenerateDocs(SyntaxElement.SIMPLE_PROPERTY_EXPRESSION, clazz, info);
    if (syntaxes == null) {
      return;
    }
    Class returnClazz = getExpressionReturnType(clazz);
    if (returnClazz == null) {
      Umbaska.logFail(Umbaska.debug ? null : clazz, new Object[] { "Couldn't register this SimplePropertyExpression because the returnClazz is null" });
    }
    Skript.registerExpression(clazz, returnClazz, ExpressionType.PROPERTY, syntaxes.value());
  }
  
  public static void propertyExpression(Class<? extends PropertyExpression> clazz, Class<? extends PropertyExpression> info)
  {
    Syntaxes syntaxes = validateAndGenerateDocs(SyntaxElement.PROPERTY_EXPRESSION, clazz, info);
    if (syntaxes == null) {
      return;
    }
    Class returnClazz = getExpressionReturnType(clazz);
    if (returnClazz == null) {
      Umbaska.logFail(Umbaska.debug ? null : clazz, new Object[] { "Couldn't register this PropertyExpression because the returnClazz is null" });
    }
    Skript.registerExpression(clazz, returnClazz, ExpressionType.PROPERTY, syntaxes.value());
  }
  
  private static Syntaxes validateAndGenerateDocs(SyntaxElement element, Class<?> clazz, Class<?> info)
  {
    Integer result = validateAndGenerateDocsInner(element, clazz, info);
    Syntaxes syntaxes = (Syntaxes)info.getAnnotation(Syntaxes.class);
    if ((generateDocs) && ((result.intValue() == 0) || (result.intValue() == 1)))
    {
      Name name = (Name)info.getAnnotation(Name.class);
      if ((name == null) || (name.value().isEmpty()))
      {
        Umbaska.logDocs(Umbaska.debug ? null : clazz, new Object[] { "WARNING > The Name annotation of this ", element.toString(), " is ", name == null ? "not set" : "empty", ", so it won't be added to the documentation" });
      }
      else
      {
        String urlName = "";
        String urlSyntax = "";
        for (String syntax : syntaxes.value()) {
          urlSyntax = "||" + urlSyntax + syntax;
        }
        urlSyntax = urlSyntax.substring(2, urlSyntax.length());
        try
        {
          urlName = URLEncoder.encode(((Name)info.getAnnotation(Name.class)).value(), "UTF-8");
          urlSyntax = URLEncoder.encode(urlSyntax, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
          e.printStackTrace();
        }
        String args = "?type=" + element.getDocsURLName() + "&name=" + urlName + "&syntax=" + urlSyntax;
        if (!docList.contains(args))
        {
          docList.add(args);
          Umbaska.logDocs(Umbaska.debug ? null : clazz, new Object[] { "Added to array: ", args });
        }
        else
        {
          Umbaska.logDocs(Umbaska.debug ? null : clazz, new Object[] { "The array already contained: ", args });
        }
      }
    }
    if (result.intValue() == 0)
    {
      registeredAmount.put(element, Integer.valueOf(((Integer)registeredAmount.get(element)).intValue() + 1));
      return syntaxes;
    }
    if (result.intValue() == 2) {
      failedAmount.put(element, Integer.valueOf(((Integer)failedAmount.get(element)).intValue() + 1));
    }
    return null;
  }
  
  protected static Integer validateAndGenerateDocsInner(SyntaxElement element, Class<?> clazz, Class<?> info)
  {
    Name name = (Name)info.getAnnotation(Name.class);
    Syntaxes syntaxes = (Syntaxes)info.getAnnotation(Syntaxes.class);
    DontRegister dontRegister = (DontRegister)clazz.getAnnotation(DontRegister.class);
    Dependency dependency = (Dependency)info.getAnnotation(Dependency.class);
    AtLeast atLeast = (AtLeast)info.getAnnotation(AtLeast.class);
    if (dontRegister != null)
    {
      Umbaska.logDebug(clazz, new Object[] { "Detected annotation DontRegister. Aborting." });
      return Integer.valueOf(1);
    }
    String clazzName = clazz.getName();
    if (Version.parse(clazzName.substring(clazzName.length() - 8, clazzName.length() - 6)) != null)
    {
      Umbaska.logDebug(clazz, new Object[] { "This class is only for one MC version, and should have been already registered before" });
      
      return Integer.valueOf(1);
    }
    if ((atLeast != null) && 
      (!Version.isAtLeast(atLeast)))
    {
      Umbaska.logDebug(clazz, new Object[] { "This element requires Minecraft server core version greater than ", atLeast.value(), " while your is ", Version.serverVersion });
      
      return Integer.valueOf(1);
    }
    if (dependency != null)
    {
      plugin = pluginManager.getPlugin(dependency.value());
      if (plugin == null)
      {
        Umbaska.logDebug(new Object[] { "Couldn't register this ", element.toString(), " because the dependency ", dependency.value(), " cannot be resolved" });
        
        return Integer.valueOf(1);
      }
    }
    if (syntaxes == null)
    {
      if (name == null) {
        Umbaska.logFail(Umbaska.debug ? null : clazz, new Object[] { "Couldn't register this ", element.toString(), " because the Syntaxes annotation is null" });
      } else {
        Umbaska.logFail(Umbaska.debug ? null : clazz, new Object[] { "Couldn't register this ", element.toString(), " with name ", name.value(), " because the Syntaxes annotation is null" });
      }
      return Integer.valueOf(2);
    }
    int i = 0;
    for (String syntax : syntaxes.value())
    {
      i++;
      if (i <= 1) {
        Umbaska.logDebug(new Object[] { "With Syntax: ", syntax });
      } else {
        Umbaska.logDebug(new Object[] { "And: ", syntax });
      }
    }
    return Integer.valueOf(0);
  }
  
  public static boolean tryGenerateDocs(String givenPassword, CommandSender sender, String urlStart)
  {
    try
    {
      if (!DocsPassword.checkDevPassword(givenPassword)) {
        return false;
      }
    }
    catch (NoSuchAlgorithmException|InvalidKeySpecException e)
    {
      e.printStackTrace();
    }
    if ((sender instanceof Player)) {
      sender.sendMessage(ChatColor.YELLOW + "Okay, check the console.");
    }
    Bukkit.getScheduler().runTaskAsynchronously(Umbaska.get(), new Runnable()
    {
      public void run()
      {
        for (String s : Register.docList) {
          try
          {
            URL verUrl = new URL(this.val$urlStart + s);
            Umbaska.logDocs(new Object[] { "Opening URL: " + verUrl });
            HttpURLConnection httpCon = (HttpURLConnection)verUrl.openConnection();
            httpCon.setRequestProperty("User-Agent", "Mozilla/5.0");
            InputStream inputStream = httpCon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            
            Umbaska.logDocs(new Object[] { "The response from server is: ", bufferedReader.readLine() });
            inputStream.close();
            bufferedReader.close();
          }
          catch (IOException e)
          {
            if (e.getCause() != null)
            {
              Umbaska.logDocs(new Object[] { "Caught an IOException: ", e.getCause().getMessage() });
            }
            else
            {
              Umbaska.logDocs(new Object[] { "Caught an IOException:" });
              e.printStackTrace();
            }
          }
        }
      }
    });
    return true;
  }
  
  private static Class getExpressionReturnType(Class<?> clazz)
  {
    try
    {
      Method method = clazz.getDeclaredMethod("getReturnType", new Class[0]);
      Object o = clazz.newInstance();
      Object returnType = method.invoke(o, new Object[0]);
      return (Class)returnType;
    }
    catch (NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException ex)
    {
      ex.printStackTrace();
    }
    return null;
  }
}
