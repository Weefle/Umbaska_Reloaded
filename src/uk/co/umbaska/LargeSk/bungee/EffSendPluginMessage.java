package uk.co.umbaska.LargeSk.bungee;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.LargeSk.LargeSk;

public class EffSendPluginMessage
  extends Effect
{
  private Expression<String> msg;
  private Expression<String> srv;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.msg = (Expression<String>) expr[0];
    this.srv = (Expression<String>) expr[1];
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

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Send Proxy Plugin Message";
}
}
