package uk.co.umbaska.Attributes;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;


public class EffSetAttribute
  extends Effect
{
  private Expression<Entity> entities;
  private Expression<Attribute> attribute;
  private Expression<Number> value;
  
  protected void execute(Event event)
  {
    Entity[] ents = (Entity[])this.entities.getAll(event);
    Number b = (Number)this.value.getSingle(event);
    Attribute ab = (Attribute)this.attribute.getSingle(event);
    for (Entity e : ents) {
      ((LivingEntity)e).getAttribute(ab).setBaseValue(b.doubleValue());
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set Entity Attribute_V1_8_R1";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.attribute = expressions[0];
    this.entities = expressions[1];
    this.value = expressions[2];
    return true;
  }
}
