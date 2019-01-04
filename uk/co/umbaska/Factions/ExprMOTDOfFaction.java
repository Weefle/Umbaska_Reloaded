package uk.co.umbaska.Factions;

import org.bukkit.event.Event;

import com.massivecraft.factions.entity.Faction;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprMOTDOfFaction extends SimplePropertyExpression<Faction, String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public String convert(Faction fac)
  {
    return fac.getMotd();
  }
  
  protected String getPropertyName()
  {
    return "faction name";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Faction fac = (Faction)getExpr().getSingle(e);
    if (fac == null)
      return;
    String motd = (String)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      fac.setMotd(motd);
    }
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    return null;
  }
}
