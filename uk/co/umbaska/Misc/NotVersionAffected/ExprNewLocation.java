package uk.co.umbaska.Misc.NotVersionAffected;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;



public class ExprNewLocation
  extends SimpleExpression<Location>
{
  private Expression<String> cmd;
  private Expression<Number> xx;
  private Expression<Number> yy;
  private Expression<Number> zz;
  
  public Class<? extends Location> getReturnType()
  {
    return Location.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.cmd = (Expression<String>) args[3];
    this.xx = (Expression<Number>) args[0];
    this.yy = (Expression<Number>) args[1];
    this.zz = (Expression<Number>) args[2];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "New location";
  }
  

  @Nullable
  protected Location[] get(Event arg0)
  {
    String c = (String)this.cmd.getSingle(arg0);
    double x = ((Double)this.xx.getSingle(arg0)).doubleValue();
    double y = ((Double)this.yy.getSingle(arg0)).doubleValue();
    double z = ((Double)this.zz.getSingle(arg0)).doubleValue();
    if (Bukkit.getWorlds().contains(Bukkit.getWorld(c))) {
      Location out = new Location(Bukkit.getWorld(c), x, y, z);
      return new Location[] { out };
    }
    
    Skript.error(Skript.SKRIPT_PREFIX + "Unknown World!");
    return new Location[] { ((World)Bukkit.getWorlds().get(0)).getSpawnLocation() };
  }
}
