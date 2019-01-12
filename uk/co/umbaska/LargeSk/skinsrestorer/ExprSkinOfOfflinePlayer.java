package uk.co.umbaska.LargeSk.skinsrestorer;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import skinsrestorer.shared.api.SkinsRestorerAPI;
import uk.co.umbaska.Registration.Syntaxes;

@Syntaxes({"skin of %offlineplayer%", "%offlineplayer%'s skin"})
public class ExprSkinOfOfflinePlayer
  extends PropertyExpression<OfflinePlayer, String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  @Nullable
  public String convert(OfflinePlayer op)
  {
    return SkinsRestorerAPI.getSkinName(op.getName());
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    OfflinePlayer op = (OfflinePlayer)getExpr().getSingle(e);
    if (mode == Changer.ChangeMode.SET) {
      SkinsRestorerAPI.setSkin(op.getName(), (String)delta[0]);
    } else if ((mode == Changer.ChangeMode.REMOVE) && 
      (SkinsRestorerAPI.hasSkin(op.getName()))) {
      SkinsRestorerAPI.setSkin(op.getName(), op.getName());
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.REMOVE)) {
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    }
    return null;
  }

@Override
public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Custom Skin of OfflinePlayer";
}

@Override
protected String[] get(Event arg0, OfflinePlayer[] arg1) {
	// TODO Auto-generated method stub
	return null;
}
}
