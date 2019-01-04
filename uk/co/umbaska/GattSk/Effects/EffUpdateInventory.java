package uk.co.umbaska.GattSk.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;










public class EffUpdateInventory
  extends Effect
{
  private Expression<Player> players;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.players = (Expression<Player>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "Update Inventory";
  }
  
  protected void execute(Event arg0)
  {
    Player[] players = (Player[])this.players.getAll(arg0);
    for (Player p : players) {
      p.updateInventory();
    }
  }
}
