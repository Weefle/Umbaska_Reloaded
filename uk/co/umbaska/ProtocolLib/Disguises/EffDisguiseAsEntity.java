package uk.co.umbaska.ProtocolLib.Disguises;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;
import uk.co.umbaska.Utils.Disguise.DisguiseAPI;
import uk.co.umbaska.Utils.Disguise.EntityDisguise;




public class EffDisguiseAsEntity
  extends Effect
{
  private Expression<uk.co.umbaska.Utils.Disguise.EntityDisguise> enttype;
  private Expression<Entity> entity;
  
  protected void execute(Event event)
  {
    Entity[] p = (Entity[])this.entity.getAll(event);
    EntityType e = ((uk.co.umbaska.Utils.Disguise.EntityDisguise)this.enttype.getSingle(event)).getEntityType();
    if ((p == null) || (e == null)) {
      return;
    }
    for (Entity p1 : p) {
      uk.co.umbaska.Utils.Disguise.DisguiseUTIL.EntityDisguise entDisguise = new uk.co.umbaska.Utils.Disguise.DisguiseUTIL.EntityDisguise(p1, e);
      ((DisguiseAPI)Main.disguiseAPI).disguisePlayer(p1, entDisguise, ((DisguiseAPI)Main.disguiseAPI).online());
    }
  }
  


  public String toString(Event event, boolean b)
  {
    return "Hide Entity";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.enttype = (Expression<EntityDisguise>) expressions[1];
    this.entity = (Expression<Entity>) expressions[0];
    return true;
  }
}
