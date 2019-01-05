package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;
import uk.co.umbaska.Utils.Scatter;

public class EffScatter extends Effect
{
  private Expression<Entity> entities;
  private Expression<Integer> x;
  private Expression<Integer> z;
  private Expression<Integer> radius;
  private Expression<Integer> delay;
  private Expression<World> world;
  private Expression<ItemStack> bad;
  
  protected void execute(Event event)
  {
    Entity[] p = (Entity[])this.entities.getAll(event);
    ItemStack[] bad = (ItemStack[])this.bad.getAll(event);
    new Scatter(Main.plugin, (World)this.world.getSingle(event), ((Integer)this.radius.getSingle(event)).intValue(), ((Integer)this.x.getSingle(event)).intValue(), ((Integer)this.z.getSingle(event)).intValue(), bad, ((Integer)this.delay.getSingle(event)).intValue(), p);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Open Inventory";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.entities = (Expression<Entity>) expressions[0];
    this.x = (Expression<Integer>) expressions[1];
    this.z = (Expression<Integer>) expressions[2];
    this.world = (Expression<World>) expressions[3];
    this.radius = (Expression<Integer>) expressions[4];
    this.bad = (Expression<ItemStack>) expressions[5];
    this.delay = (Expression<Integer>) expressions[6];
    return true;
  }
}
