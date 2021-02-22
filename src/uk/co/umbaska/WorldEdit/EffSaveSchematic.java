package uk.co.umbaska.WorldEdit;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;



public class EffSaveSchematic
  extends Effect
{
  private Expression<Player> player;
  private Expression<String> schemname;
  
  protected void execute(Event event)
  {
    Player l = (Player)this.player.getSingle(event);
    String name = (String)this.schemname.getSingle(event);
    Schematic.save(l, name);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Save Schematic";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[1];
    this.schemname = (Expression<String>) expressions[0];
    return true;
  }
}
