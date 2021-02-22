package uk.co.umbaska.Misc.NotVersionAffected;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;








public class ExprPermissionOfCommand
  extends SimpleExpression<String>
{
  private Expression<String> cmd;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.cmd = (Expression<String>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "permission of cmd";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    String c = (String)this.cmd.getSingle(arg0);
    Field f = null;
    try {
      f = Bukkit.getServer().getClass().getDeclaredField("SimpleCommandMap");
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    f.setAccessible(true);
    SimpleCommandMap cmap = null;
    try {
      cmap = (SimpleCommandMap)f.get(Bukkit.getServer());
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    Command ccc = cmap.getCommand(c);
    String out = ccc.getPermission();
    return new String[] { out };
  }
}
