package uk.co.umbaska.LargeSk.bungee;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.LargeSk.LargeSk;
import uk.co.umbaska.Registration.BungeeCord;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("Send Proxy Plugin Message")
@Syntaxes({"proxy send %string% [to %-string%]"})
@BungeeCord
public class EffSendPluginMessage
  extends UmbaskaEffect
{
  private Expression<String> msg;
  private Expression<String> srv;
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.msg = expr[0];
    this.srv = expr[1];
    return true;
  }
  
  protected void execute(Event e)
  {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("Forward");
    String rec = (String)this.srv.getSingle(e);
    String msag = (String)this.msg.getSingle(e);
    if ((rec == null) || (rec.equalsIgnoreCase("all")) || (rec.isEmpty())) {
      rec = "ALL";
    }
    out.writeUTF(rec);
    out.writeUTF("LargeSkEff");
    out.writeUTF(msag);
    
    Player pl = (Player)Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
    if (pl == null)
    {
      Skript.error("You can't send proxy messages when there are no players online.");
      return;
    }
    pl.sendPluginMessage(LargeSk.getPluginInstance(), "BungeeCord", out.toByteArray());
  }
}
