package uk.co.umbaska.Misc;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import uk.co.umbaska.Utils.TitleManager.TitleManager;




public class EffTabList
  extends Effect
  implements Listener
{
  private Expression<Player> Players;
  private Expression<String> format;
  private boolean footer;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.footer = (parse.mark == 0);
    this.format = (Expression<String>) exprs[0];
    this.Players = (Expression<Player>) exprs[1];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "tablist header 1.8";
  }
  


  protected void execute(Event event)
  {
    Player[] playerlist = (Player[])this.Players.getAll(event);
    String format = this.format.toString(event, true);
    for (Player p : playerlist) {
		if (this.footer == false) {
			PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
			IChatBaseComponent tabTitle = ChatSerializer.a(TitleManager.newheader.toString());
			IChatBaseComponent tabFoot = ChatSerializer.a(TitleManager.newfooter.toString());
			PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
			try
			{
				Field field = headerPacket.getClass().getDeclaredField("b");
				field.setAccessible(true);
				field.set(headerPacket, tabFoot);
            } catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				connection.sendPacket(headerPacket);
			}
		}
		if (this.footer == true) {
            TitleManager.newheader = ChatSerializer.a(format);
			if (TitleManager.newfooter == null) {
                TitleManager.newfooter = ChatSerializer.a("Default");
			}
			PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
			IChatBaseComponent tabTitle = ChatSerializer.a(TitleManager.newheader.toString());
			IChatBaseComponent tabFoot = ChatSerializer.a(TitleManager.newfooter.toString());
			PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
			try
			{
				Field field = headerPacket.getClass().getDeclaredField("b");
				field.setAccessible(true);
				field.set(headerPacket, tabFoot);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				connection.sendPacket(headerPacket);
			}
		}
	}
  }
}
