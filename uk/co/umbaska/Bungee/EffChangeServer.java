package uk.co.umbaska.Bungee;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;

public class EffChangeServer extends Effect
{
  private Expression<Player> player;
  private Expression<String> server;
  
  protected void execute(Event event)
  {
    String s = (String)this.server.getSingle(event);
    try
    {
      for (Player p : (Player[])this.player.getAll(event))
      {
        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(msg);
        out.writeUTF("Connect");
        out.writeUTF(s);
        out.close();
        Main.messenger.sendTo(msg.toByteArray(), new Player[] { p });
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Change server";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[0];
    this.server = expressions[1];
    return true;
  }
}
