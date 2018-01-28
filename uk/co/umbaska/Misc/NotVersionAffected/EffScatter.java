package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
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
    Scatter scatter = new Scatter(Main.plugin, (World)this.world.getSingle(event), ((Integer)this.radius.getSingle(event)).intValue(), ((Integer)this.x.getSingle(event)).intValue(), ((Integer)this.z.getSingle(event)).intValue(), bad, ((Integer)this.delay.getSingle(event)).intValue(), p);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Open Inventory";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.entities = expressions[0];
    this.x = expressions[1];
    this.z = expressions[2];
    this.world = expressions[3];
    this.radius = expressions[4];
    this.bad = expressions[5];
    this.delay = expressions[6];
    return true;
  }
}
