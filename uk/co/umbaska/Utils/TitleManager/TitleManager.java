package uk.co.umbaska.Utils.TitleManager;

import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleManager implements org.bukkit.event.Listener
{
  public static Class<?> nmsChatSerializer = Reflection.getNMSClass("ChatSerializer");
  public static int VERSION = 47;
  public static IChatBaseComponent newfooter;
  public static IChatBaseComponent newheader;
  
  public static void sendTitle(Player p, String title) {
    try {
      PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
      title = ChatColor.translateAlternateColorCodes('&', title);
      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction.TITLE, titleSub);
      connection.sendPacket(packetPlayOutSubTitle);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void sendActionTitle(Player p, String action) {
    try {
      PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
      action = ChatColor.translateAlternateColorCodes('&', action);
      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + action + "\"}");
      Byte slot = Byte.valueOf((byte)2);
      net.minecraft.server.v1_9_R1.PacketPlayOutChat packetPlayOutSubTitle = new net.minecraft.server.v1_9_R1.PacketPlayOutChat(titleSub, slot.byteValue());
      connection.sendPacket(packetPlayOutSubTitle);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void sendSubTitle(Player p, String subtitle) {
    try { PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
      connection.sendPacket(packetPlayOutSubTitle);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
