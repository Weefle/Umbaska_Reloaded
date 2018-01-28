package uk.co.umbaska.Attributes;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;


public class EffRemoveAttributeModifer
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
      ((LivingEntity)e).getAttribute(ab).removeModifier(b);
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Remove Modifier";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.attribute = expressions[1];
    this.entities = expressions[2];
    this.modifier = expressions[0];
    return true;
  }
}
