package uk.co.umbaska.Attributes;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;


public class EffAddAttributeModifer
  extends Effect
{
  private Expression<Entity> entities;
  private Expression<Attribute> attribute;
  private Expression<AttributeModifier> modifier;
  
  protected void execute(Event event)
  {
    Entity[] ents = (Entity[])this.entities.getAll(event);
    AttributeModifier b = (AttributeModifier)this.modifier.getSingle(event);
    Attribute ab = (Attribute)this.attribute.getSingle(event);
    for (Entity e : ents) {
      ((LivingEntity)e).getAttribute(ab).addModifier(b);
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Add Modifier";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.attribute = (Expression<Attribute>) expressions[1];
    this.entities = (Expression<Entity>) expressions[2];
    this.modifier = (Expression<AttributeModifier>) expressions[0];
    return true;
  }
}
