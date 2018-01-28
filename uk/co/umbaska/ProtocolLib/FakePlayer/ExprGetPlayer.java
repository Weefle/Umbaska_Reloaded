package uk.co.umbaska.ProtocolLib.FakePlayer;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.ProtocolLib.FakePlayerTracker;

public class ExprGetPlayer extends SimpleExpression<Player>
{
  private Expression<String> player;
  
  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends Player> getReturnType() {
    return Player.class;
  }
  
  @Nullable
  protected Player[] get(Event arg0)
  {
    if (FakePlayerTracker.getPlayer((String)this.player.getSingle(arg0)) != null) {
      return new Player[] { FakePlayerTracker.getPlayer((String)this.player.getSingle(arg0)).getBukkitEntity().getPlayer() };
    }
    return null;
  }
  


  public String toString(Event event, boolean b)
  {
    return "Get player from fake player";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[0];
    return true;
  }
}
