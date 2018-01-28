package uk.co.umbaska.ArmourStands;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Event;



public class EffSpawnArmorStand
  extends Effect
{
  private Expression<Location> loc;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.loc = exprs[0];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "spawn armor stand";
  }
  

  protected void execute(Event event)
  {
    Location[] loc = (Location[])this.loc.getAll(event);
    
    if (loc == null) {
      return;
    }
    
    for (Location l : loc) {
      ArmorStand stand = (ArmorStand)l.getWorld().spawn(l, ArmorStand.class);
      ch.njol.skript.effects.EffSpawn.lastSpawned = stand;
      uk.co.umbaska.Main.armorStand = stand;
    }
  }
}
