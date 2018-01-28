package uk.co.umbaska.System;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprPing extends ch.njol.skript.lang.util.SimpleExpression<Integer>
{
  private static String packageName = org.bukkit.Bukkit.getServer().getClass().getPackage().getName();
  private static String version = packageName.substring(packageName.lastIndexOf(".") + 1);
  private Expression<Player> player;
  
  protected Integer[] get(Event event)
  {
    Player p = (Player)this.player.getSingle(event);
    if (p == null) return null;
    try {
      Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
      Object cp = craftPlayer.cast(p);
      Object handle = craftPlayer.getMethod("getHandle", new Class[0]).invoke(cp, new Object[0]);
      Integer ping = Integer.valueOf(((Integer)handle.getClass().getField("ping").get(handle)).intValue());
      return new Integer[] { ping };
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tmp123_120;
  }
  


  public boolean isSingle() { return true; }
  
  public Class<? extends Integer> getReturnType() { return Integer.class; }
  
  public String toString(Event event, boolean b) {
    return getClass().getName();
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[0];
    return true;
  }
}
