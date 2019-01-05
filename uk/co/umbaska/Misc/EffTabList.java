package uk.co.umbaska.Misc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Utils.TitleManager.Reflection;




public class EffTabList
  extends Effect
  implements Listener
{
  private Expression<Player> Players;
  private Expression<String> header;
  private Expression<String> footer;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.header = (Expression<String>) exprs[0];
    this.footer = (Expression<String>) exprs[1];
    this.Players = (Expression<Player>) exprs[2];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "tablist header and footer";
  }
  


  protected void execute(Event event)
  {
    Player[] playerlist = (Player[])this.Players.getAll(event);
    String header = this.header.getSingle(event);
    String footer = this.footer.getSingle(event);
    for (Player p : playerlist) {
		try {
			Class<?> packetPlayOutPlayerListHeaderFooter = Reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
	    	Constructor<?> packetPlayOutPlayerListHeaderFooterConstructor;
			packetPlayOutPlayerListHeaderFooterConstructor = packetPlayOutPlayerListHeaderFooter.getConstructor();
			Constructor<?> chatComponentConstructor = Reflection.getNMSClass("ChatComponentText").getConstructor(String.class);
	    	Object componentHeader = chatComponentConstructor.newInstance(header);
			Object componentFooter = chatComponentConstructor.newInstance(footer);
			Object packet = packetPlayOutPlayerListHeaderFooterConstructor.newInstance();
			Field a = packet.getClass().getDeclaredField("a");
			a.setAccessible(true);
			a.set(packet, componentHeader);

			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(packet, componentFooter);
			Method sendPacket = Reflection.getConnection ( p ).getClass().getMethod ( "sendPacket", Reflection.getNMSClass ( "Packet" ));
			sendPacket.invoke (Reflection.getConnection(p), packet );
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		}
	}
  }
