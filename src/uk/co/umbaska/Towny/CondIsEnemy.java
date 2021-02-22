package uk.co.umbaska.Towny;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;










public class CondIsEnemy
  extends Condition
{
  private Expression<String> twn1;
  private Expression<String> twn2;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int i, Kleenean kl, SkriptParser.ParseResult pr)
  {
    this.twn1 = (Expression<String>) expr[0];
    return true;
  }
  
  public String toString(@Nullable Event e, boolean b)
  {
    return "Enemy Relation of a town";
  }
  

  public boolean check(Event e)
  {
    String tw1 = (String)this.twn1.getSingle(e);
    String tw2 = (String)this.twn2.getSingle(e);
    Town t1 = null;
    Town t2 = null;
    try {
      t1 = TownyUniverse.getDataSource().getTown(tw1);
    } catch (NotRegisteredException e1) {
      e1.printStackTrace();
    }
    try {
      t2 = TownyUniverse.getDataSource().getTown(tw2);
    } catch (NotRegisteredException e1) {
      e1.printStackTrace();
    }
    Nation n1 = null;
    try {
      n1 = t1.getNation();
    } catch (NotRegisteredException e1) {
      e1.printStackTrace();
    }
    Nation n2 = null;
    try {
      n2 = t2.getNation();
    } catch (NotRegisteredException e1) {
      e1.printStackTrace();
    }
    Boolean out = Boolean.valueOf(false);
    if (n1.hasEnemy(n2)) {
      out = Boolean.valueOf(true);
      return out.booleanValue();
    }
    out = Boolean.valueOf(false);
    return out.booleanValue();
  }
}
