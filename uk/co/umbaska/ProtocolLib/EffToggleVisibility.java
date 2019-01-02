package uk.co.umbaska.ProtocolLib;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Managers.Effects;



public class EffToggleVisibility
  extends Effect
{
  private Expression<Entity> entity;
  private Expression<Player> player;
  
  protected void execute(Event event)
  {
    Player[] p = (Player[])this.player.getAll(event);
    Entity[] e = (Entity[])this.entity.getAll(event);
    if (p == null) {
      return;
    }
    for (Player player : p) {
      for (Entity ent : e) {
        if (ent.getType() != EntityType.PLAYER) {
          Effects.enthider.toggleEntity(player, ent);
        }
      }
    }
  }
  


  public String toString(Event event, boolean b)
  {
    return "Toggle Entity";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.entity = (Expression<Entity>) expressions[0];
    this.player = (Expression<Player>) expressions[1];
    return true;
  }
}
