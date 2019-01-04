package uk.co.umbaska.GattSk.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;









public class EffCustomName
  extends Effect
{
  private Expression<Entity[]> entities;
  private Expression<String> name;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.entities = (Expression<Entity[]>) args[0];
    this.name = (Expression<String>) args[1];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "create new objective";
  }
  
  protected void execute(Event arg0)
  {
    Entity[] entities = (Entity[])this.entities.getSingle(arg0);
    String name = (String)this.name.getSingle(arg0);
    for (Entity e : entities) {
      e.setCustomName(name);
      e.setCustomNameVisible(true);
    }
  }
}
