package uk.co.umbaska.LargeSk.skinsrestorer;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import skinsrestorer.shared.api.SkinsRestorerAPI;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaPropertyExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Custom Skin of OfflinePlayer")
@Syntaxes({"skin of %offlineplayer%", "%offlineplayer%'s skin"})
public class ExprSkinOfOfflinePlayer
  extends SimpleUmbaskaPropertyExpression<OfflinePlayer, String>
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
}
