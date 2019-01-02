package uk.co.umbaska.ProtocolLib.Disguises;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;
import uk.co.umbaska.Utils.Disguise.DisguiseAPI;



public class EffUndisguise
  extends Effect
{
  private Expression<Entity> entity;
  
  protected void execute(Event event)
  {
    Entity[] p = (Entity[])this.entity.getAll(event);
    if (p == null) {
      return;
    }
    for (Entity p1 : p) {
      ((DisguiseAPI)Main.disguiseAPI).unDisguisePlayer(p1);
    }
  }
  


  public String toString(Event event, boolean b)
  {
    return "Hide Entity";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.entity = (Expression<Entity>) expressions[0];
    return true;
  }
}
