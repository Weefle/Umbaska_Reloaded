package uk.co.umbaska.Utils.TitleManager;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R2.PlayerConnection;

public class TitleManager implements org.bukkit.event.Listener
{
 // public static Class<?> nmsChatSerializer = Reflection.getNMSClass("ChatSerializer");
  public static int VERSION = 47;
  public static IChatBaseComponent newfooter;
  public static IChatBaseComponent newheader;
  
  public static void sendTitle(Player p, String title) {
    try {
      PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
      title = ChatColor.translateAlternateColorCodes('&', title);
      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.TITLE, titleSub);
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
      net.minecraft.server.v1_9_R2.PacketPlayOutChat packetPlayOutSubTitle = new net.minecraft.server.v1_9_R2.PacketPlayOutChat(titleSub, slot.byteValue());
      connection.sendPacket(packetPlayOutSubTitle);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void sendSubTitle(Player p, String subtitle) {
    try { PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
      connection.sendPacket(packetPlayOutSubTitle);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
