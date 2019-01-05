package uk.co.umbaska.Misc;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import uk.co.umbaska.Utils.TitleManager.TitleManager;


public class EffSendTitle
  extends Effect
  implements Listener
{
  private Expression<String> Title;
  private Expression<String> Subtitle;
  private Expression<Player> Players;
  private Expression<Number> afadein;
  private Expression<Number> afadeout;
  private Expression<Number> astay;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.Title = (Expression<String>) exprs[0];
    this.Subtitle = (Expression<String>) exprs[1];
    this.Players = (Expression<Player>) exprs[2];
    
    this.afadein = (Expression<Number>) exprs[3];
    this.astay = (Expression<Number>) exprs[4];
    this.afadeout = (Expression<Number>) exprs[5];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "titles";
  }
  

  protected void execute(Event event)
  {
    String newtitle = (String)this.Title.getSingle(event);
    String newsubtitle = (String)this.Subtitle.getSingle(event);
    Player[] playerlist = (Player[])this.Players.getAll(event);
    
    Number newfadein = (Number)this.afadein.getSingle(event);
    Number newfadeout = (Number)this.afadeout.getSingle(event);
    Number newstay = (Number)this.astay.getSingle(event);
    

    for (Player p : playerlist) {
      try {
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, newfadein.intValue(), newstay.intValue(), newfadeout.intValue());
        connection.sendPacket(packetPlayOutTimes);
      } catch (Exception e) {
        e.printStackTrace();
      }
      TitleManager.sendSubTitle(p, newsubtitle);
      TitleManager.sendTitle(p, newtitle);
    }
  }
}
