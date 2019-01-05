package uk.co.umbaska.Managers;

import java.util.Locale;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.PatternType;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.PluginBase;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.party.PartyManager;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.store.EntityInternal;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import ca.thederpygolems.armorequip.ArmourEquipEvent;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import uk.co.umbaska.Main;
import uk.co.umbaska.Enums.BukkitEffectEnum;
import uk.co.umbaska.Enums.InventoryTypes;
import uk.co.umbaska.Enums.Operation;
import uk.co.umbaska.Enums.ParticleEnum;
import uk.co.umbaska.JSON.JSONMessage;
import uk.co.umbaska.JSON.JsonBuilder;
import uk.co.umbaska.Misc.Date.DayOfWeek;
import uk.co.umbaska.Utils.EnumClassInfo;
import uk.co.umbaska.Utils.Disguise.EntityDisguise;


public class Enums
{
  public static Boolean debugInfo = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("debug_info"));
  private static String version = Register.getVersion();
  
  @SuppressWarnings("unused")
private static void registerEnum(String cls, String name, Boolean multiversion) {
    if (Skript.isAcceptRegistrations()) {
      if (multiversion.booleanValue())
      {
        @SuppressWarnings("rawtypes")
		Class newCls = Register.getClass(cls);
        if (newCls == null) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Enum for " + name + " due to \nWrong Spigot/Bukkit Version!");
        }
        if (debugInfo.booleanValue()) {
          Bukkit.getLogger().info("Umbaska »»» Registered Enum for " + name + " for Version " + version);
        }
        registerEnum(newCls, name);
      } else {
        try {
          registerEnum(Class.forName(cls), name);
        } catch (ClassNotFoundException e) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Enum for " + name + " due to \nWrong Spigot/Bukkit Version!");
        }
      }
    } else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Enum for " + name + " due to \nSkript Not Accepting Registrations");
    }
  }
  
  @SuppressWarnings("unchecked")
private static void registerEnum(@SuppressWarnings("rawtypes") Class cls, String name) {
    if (Skript.isAcceptRegistrations()) {
      EnumClassInfo.create(cls, name).register();
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Enum for " + name + " due to \nSkript Not Accepting Registrations");
    }
  }
  
  @SuppressWarnings({ "unused", "unchecked" })
private static void registerEnum(@SuppressWarnings("rawtypes") Class cls, String name, @SuppressWarnings("rawtypes") EventValueExpression defaultExpression) {
    if (Skript.isAcceptRegistrations()) {
      EnumClassInfo.create(cls, name, defaultExpression).register();
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Enum for " + name + " due to \nSkript Not Accepting Registrations");
    }
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static void runRegister() {
    registerEnum(InventoryTypes.class, "umbaskainv");
    registerEnum(ParticleEnum.class, "particleenum");
    registerEnum(BukkitEffectEnum.class, "bukkiteffect");
    
    registerEnum(Locale.class, "locale");
    registerEnum(DayOfWeek.class, "dayofweek");
    registerEnum(PatternType.class, "bannerpattern");
    registerEnum(EntityDisguise.class, "entitydisguise");
    registerEnum(Operation.class, "nbtoperation");
    registerEnum(ArmourEquipEvent.EquipMethod.class, "equipmethod");
   // registerEnum(ClickType.class, "clicktype");
    registerEnum(EntityEffect.class, "entityeffect");
   // EnumClassInfo.create(Material.class, "material").after(new String[] { "block" }).register();
    

    registerEnum(AttributeModifier.class, "attributemodifier");
    registerEnum(Attribute.class, "attribute");
    registerEnum(BarFlag.class, "barflag");
    registerEnum(BarColor.class, "barcolor");
    registerEnum(BarStyle.class, "barstyle");
    
    Classes.registerClass(new ClassInfo(BossBar.class, "bossbar").parser(new Parser()
    {
      public String toString(Object o, int i)
      {
        return ((BossBar)o).toString();
      }
      
      public String toVariableNameString(Object o)
      {
        return "BossBar:" + ((BossBar)o).toString();
      }
      
      public BossBar parse(String s, ParseContext parseContext)
      {
        return null;
      }
      
      public boolean canParse(ParseContext context)
      {
        return false;
      }
      
      @SuppressWarnings("unused")
	public String toString(BossBar bar, int i)
      {
        return bar.toString();
      }
      
      @SuppressWarnings("unused")
	public String toVariableNameString(BossBar bar)
      {
        return "BossBar: " + bar.toString();
      }
      
      public String getVariableNamePattern()
      {
        return ".+";
      }
      
    }));
    Classes.registerClass(new ClassInfo(JSONMessage.class, "umbjsonmessage").parser(new Parser()
    {
      public String toString(Object o, int i)
      {
        return ((JSONMessage)o).toString();
      }
      
      public String toVariableNameString(Object o)
      {
        return ((JSONMessage)o).toString();
      }
      
      public JsonBuilder parse(String s, ParseContext parseContext)
      {
        return null;
      }
      
      public boolean canParse(ParseContext context)
      {
        return false;
      }
      
      @SuppressWarnings("unused")
	public String toString(JSONMessage jsonMessage, int i)
      {
        return jsonMessage.toOldMessageFormat();
      }
      
      @SuppressWarnings("unused")
	public String toVariableNameString(JSONMessage jsonMessage)
      {
        return jsonMessage.toString();
      }
      
      public String getVariableNamePattern()
      {
        return ".+";
      }
    }));
    


    if (!Main.disableSkRambled.booleanValue()) {
      if ((Bukkit.getPluginManager().getPlugin("Factions") != null) && (Bukkit.getPluginManager().getPlugin("MassiveCore") != null)) {
        Classes.registerClass(new ClassInfo(Faction.class, "faction").user(new String[] { "faction" }).name("Faction").defaultExpression(new EventValueExpression(Faction.class)).parser(new Parser()
        {


          @Nullable
          public Faction parse(String s, ParseContext context)
          {


            return FactionColl.get().getByName(s);
          }
          
          public String toString(Object faction, int flags)
          {
            return ((PluginBase) faction).getName().toLowerCase();
          }
          
          public String toVariableNameString(Object faction)
          {
            return ((PluginBase) faction).getName().toLowerCase();
          }
          
          public String getVariableNamePattern()
          {
            return ".+";
          }
          
        }));
        Classes.registerClass(new ClassInfo(Rel.class, "rel").name("Rel").parser(new Parser()
        {
          @Nullable
          public Rel parse(String s, ParseContext context)
          {
            return Rel.valueOf(s);
          }
          
          public String toString(Object rel, int flags)
          {
            return rel.toString().toLowerCase();
          }
          
          public String toVariableNameString(Object rel)
          {
            return rel.toString().toLowerCase();
          }
          
          public String getVariableNamePattern()
          {
            return ".+";
          }
        }));
      }
      


      if (Bukkit.getPluginManager().getPlugin("mcMMO") != null) {
        Classes.registerClass(new ClassInfo(Party.class, "party").name("Party").parser(new Parser()
        {
          @Nullable
          public Party parse(String s, ParseContext context)
          {
            return PartyManager.getParty(s);
          }
          
          public String toString(Object party, int flags)
          {
            return ((PluginBase) party).getName().toLowerCase();
          }
          
          public String toVariableNameString(Object party)
          {
            return ((PluginBase) party).getName().toLowerCase();
          }
          
          public String getVariableNamePattern()
          {
            return ".+";
          }
          

        }));
        Classes.registerClass(new ClassInfo(SkillType.class, "skill").name("Skill").parser(new Parser()
        {
          @Nullable
          public SkillType parse(String s, ParseContext context)
          {
            try
            {
              return SkillType.valueOf(s.toUpperCase());
            }
            catch (Exception e) {}
            
            return null;
          }
          
          public String toString(Object skill, int flags)
          {
            return ((PluginBase) skill).getName().toLowerCase();
          }
          
          public String toVariableNameString(Object skill)
          {
            return ((PluginBase) skill).getName().toLowerCase();
          }
          
          public String getVariableNamePattern()
          {
            return ".+";
          }
        }));
      }
      


      if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
        Classes.registerClass(new ClassInfo(ProtectedRegion.class, "protectedregion").name("Protected Region").user(new String[] { "protectedregions?" }).defaultExpression(new EventValueExpression(ProtectedRegion.class)).parser(new Parser()
        {



          @Nullable
          public ProtectedRegion parse(String s, ParseContext context)
          {



            for (World w : Bukkit.getServer().getWorlds()) {
              if (WGBukkit.getRegionManager(w).hasRegion(s)) {
                return WGBukkit.getRegionManager(w).getRegion(s);
              }
            }
            return null;
          }
          
          public String toString(Object region, int flags)
          {
            return ((EntityInternal<Faction>) region).getId().toLowerCase();
          }
          
          public String toVariableNameString(Object region)
          {
            return ((EntityInternal<Faction>) region).getId().toLowerCase();
          }
          
          public String getVariableNamePattern()
          {
            return ".+";
          }
          
        }));
        Classes.registerClass(new ClassInfo(Flag.class, "flag").name("Flag").user(new String[] { "flags?" }).defaultExpression(new EventValueExpression(Flag.class)).parser(new Parser()
        {



          public Flag<?> parse(String s, ParseContext context)
          {


            return DefaultFlag.fuzzyMatchFlag(null, s);
          }
          
          public String toString(Object flag, int flags)
          {
            return ((PluginBase) flag).getName().toLowerCase();
          }
          
          public String toVariableNameString(Object flag)
          {
            return ((PluginBase) flag).getName().toLowerCase();
          }
          
          public String getVariableNamePattern()
          {
            return ".+";
          }
        }));
      }
    }
    




























    Main.getInstance().getLogger().info("[Umbaska > SkQuery] Registered Custom Particle Enum. Have some BACON!!!!");
  }
}
