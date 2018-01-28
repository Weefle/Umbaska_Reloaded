package uk.co.umbaska.HologramBased;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;




public class EffCreateFollowGram
  extends Effect
{
  private Expression<Entity> entitytoFollow;
  private Expression<String> Text;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.entitytoFollow = exprs[0];
    this.Text = exprs[1];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "create 'follow' gram";
  }
  
  protected void execute(Event event)
  {
    String[] text = (String[])this.Text.getAll(event);
    Entity ent = (Entity)this.entitytoFollow.getSingle(event);
    if (text == null) {
      return;
    }
    if (ent == null) {
      return;
    }
    Hologram follow = new Hologram(ent.getLocation(), text).start();
    follow.setFollowEntity(ent, true);
  }
}
