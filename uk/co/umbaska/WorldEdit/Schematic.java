package uk.co.umbaska.WorldEdit;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import java.io.File;
import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import uk.co.umbaska.Main;

public class Schematic
{
  public static void save(Player player, String schematicName)
  {
    try
    {
      File schematic = new File(Main.schemFolder + schematicName);
      File dir = new File(Main.schemFolder);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      WorldEditPlugin wep = (WorldEditPlugin)org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
      WorldEdit we = wep.getWorldEdit();
      
      LocalPlayer localPlayer = wep.wrapPlayer(player);
      LocalSession localSession = we.getSession(localPlayer);
      ClipboardHolder selection = localSession.getClipboard();
      EditSession editSession = localSession.createEditSession(localPlayer);
      
      Vector min = selection.getClipboard().getMinimumPoint();
      Vector max = selection.getClipboard().getMaximumPoint();
      
      editSession.enableQueue();
      CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
      clipboard.copy(editSession);
      SchematicFormat.MCEDIT.save(clipboard, schematic);
      editSession.flushQueue();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    } catch (DataException ex) {
      ex.printStackTrace();
    } catch (EmptyClipboardException ex) {
      ex.printStackTrace();
    }
  }
  
  public static void place(String schematicName, Location pasteLoc, Boolean ignoreAir) { if (ignoreAir.booleanValue()) {
      try {
        File dir = new File(Main.schemFolder + schematicName);
        EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
        editSession.enableQueue();
        
        SchematicFormat schematic = SchematicFormat.getFormat(dir);
        CuboidClipboard clipboard = schematic.load(dir);
        
        clipboard.place(editSession, BukkitUtil.toVector(pasteLoc), ignoreAir.booleanValue());
        editSession.flushQueue();
      } catch (DataException ex) {
        ex.printStackTrace();
      } catch (IOException ex) {
        ex.printStackTrace();
      } catch (MaxChangedBlocksException ex) {
        ex.printStackTrace();
      }
    } else {
      place(schematicName, pasteLoc);
    }
  }
  
  public static void place(String schematicName, Location pasteLoc) {
    try {
      File dir = new File(Main.schemFolder + schematicName);
      EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
      editSession.enableQueue();
      
      SchematicFormat schematic = SchematicFormat.getFormat(dir);
      CuboidClipboard clipboard = schematic.load(dir);
      
      clipboard.place(editSession, BukkitUtil.toVector(pasteLoc), false);
      editSession.flushQueue();
    } catch (DataException|IOException ex) {
      ex.printStackTrace();
    } catch (MaxChangedBlocksException ex) {
      ex.printStackTrace();
    }
  }
  
  public static void paste(String schematicName, Location pasteLoc, Boolean ignoreAir) {
    if (ignoreAir.booleanValue()) {
      try {
        File dir = new File(Main.schemFolder + schematicName);
        EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
        editSession.enableQueue();
        
        SchematicFormat schematic = SchematicFormat.getFormat(dir);
        CuboidClipboard clipboard = schematic.load(dir);
        
        clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), true, true);
        editSession.flushQueue();
      } catch (DataException|IOException ex) {
        ex.printStackTrace();
      } catch (MaxChangedBlocksException ex) {
        ex.printStackTrace();
      }
    } else {
      paste(schematicName, pasteLoc);
    }
  }
  
  public static void paste(String schematicName, Location pasteLoc) {
    try {
      File dir = new File(Main.schemFolder + schematicName);
      EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
      editSession.enableQueue();
      
      SchematicFormat schematic = SchematicFormat.getFormat(dir);
      CuboidClipboard clipboard = schematic.load(dir);
      
      clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), false, true);
      editSession.flushQueue();
    } catch (DataException|IOException ex) {
      ex.printStackTrace();
    } catch (MaxChangedBlocksException ex) {
      ex.printStackTrace();
    }
  }
}
