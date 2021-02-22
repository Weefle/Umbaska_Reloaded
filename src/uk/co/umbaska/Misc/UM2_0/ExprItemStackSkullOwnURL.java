package uk.co.umbaska.Misc.UM2_0;

import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;


public class ExprItemStackSkullOwnURL
  extends SimplePropertyExpression<ItemStack, String>
{
  public String convert(ItemStack ent)
  {
    if (ent == null)
      return null;
    return getURL(ent);
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    ItemStack ent = (ItemStack)getExpr().getSingle(e);
    if (ent == null)
      return;
    if (ent.getType() != Material.SKULL_ITEM) {
      return;
    }
    String b = (String)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      setURL(ent, b);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    return null;
  }
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  

  protected String getPropertyName()
  {
    return "skull owner url ItemStack";
  }
  
  public ItemStack setURL(ItemStack head, String url) {
    if (url.isEmpty()) return head;
    SkullMeta headMeta = (SkullMeta)head.getItemMeta();
    headMeta.setOwner("Umbaska");
    GameProfile profile = new GameProfile(UUID.randomUUID(), null);
    byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
    profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
    Field profileField = null;
    try {
      profileField = headMeta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      profileField.set(headMeta, profile);
    } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
      e1.printStackTrace();
    }
    
    head.setItemMeta(headMeta);
    return head;
  }
  
  public String getURL(ItemStack head) { SkullMeta headMeta = (SkullMeta)head.getItemMeta();
    Field profileField = null;
    String url = null;
    try {
      profileField = headMeta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      url = profileField.get(headMeta).toString();
      profileField.setAccessible(false);
    } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
      e1.printStackTrace();
    }
    return url;
  }
}
