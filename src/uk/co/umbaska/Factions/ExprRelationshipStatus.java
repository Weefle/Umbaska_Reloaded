package uk.co.umbaska.Factions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;



public class ExprRelationshipStatus
  extends SimpleExpression<Rel>
{
  private Expression<Faction> fac1;
  private Expression<Faction> fac2;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.fac1 = (Expression<Faction>) exprs[0];
    this.fac2 = (Expression<Faction>) exprs[1];
    return true;
  }
  
  protected Rel[] get(Event e)
  {
    Faction firstfac = (Faction)this.fac1.getSingle(e);
    Faction secondfac = (Faction)this.fac2.getSingle(e);
    if (firstfac == null)
      return null;
    if (secondfac == null)
      return null;
    Rel r = firstfac.getRelationTo(secondfac);
    return new Rel[] { r };
  }
  
  public Class<? extends Rel> getReturnType()
  {
    return Rel.class;
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Faction firstfac = (Faction)this.fac1.getSingle(e);
    Faction secondfac = (Faction)this.fac2.getSingle(e);
    if (firstfac == null)
      return;
    if (secondfac == null)
      return;
    Rel rel = (Rel)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      firstfac.setRelationWish(secondfac, rel);
      secondfac.setRelationWish(firstfac, rel);
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      firstfac.setRelationWish(secondfac, Rel.NEUTRAL);
      secondfac.setRelationWish(firstfac, Rel.NEUTRAL);
    }
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "relation between faction";
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { Rel.class });
  }
}
