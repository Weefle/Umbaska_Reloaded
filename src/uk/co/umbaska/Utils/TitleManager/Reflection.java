package uk.co.umbaska.Utils.TitleManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reflection
{
  public static String getVersion()
  {
	  String version = Bukkit.getServer ( ).getClass ( ).getPackage ( ).getName ( ).replace ( ".", "," ).split ( "," )[ 3 ] + ".";
    return version;
  }
  
  public static Class<?> getNMSClass(String className) {
    String fullName = "net.minecraft.server." + getVersion() + className;
    Class<?> clazz = null;
    try {
      clazz = Class.forName(fullName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return clazz;
  }
  
  public static Class<?> getOBCClass(String className) {
    String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
    Class<?> clazz = null;
    try {
      clazz = Class.forName(fullName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return clazz;
  }
  
  public static Object getHandle(Object obj) {
    try {
      return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Field getField(Class<?> clazz, String name) {
    try {
      Field field = clazz.getDeclaredField(name);
      field.setAccessible(true);
      return field;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
    for (Method m : clazz.getMethods()) {
      if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes())))) {
        m.setAccessible(true);
        return m;
      }
    }
    return null;
  }
  
  public static  Object getConnection ( Player player ) throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
	    Method getHandle = player.getClass ( ).getMethod ( "getHandle" );
	    Object nmsPlayer = getHandle.invoke ( player );
	    Field conField = nmsPlayer.getClass ( ).getField ( "playerConnection" );
	    Object con = conField.get ( nmsPlayer );
	    return con;
	}
  
  public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
    boolean equal = true;
    if (l1.length != l2.length) {
      return false;
    }
    for (int i = 0; i < l1.length; i++) {
      if (l1[i] != l2[i]) {
        equal = false;
        break;
      }
    }
    return equal;
  }
}
