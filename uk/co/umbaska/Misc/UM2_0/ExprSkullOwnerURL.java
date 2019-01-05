package uk.co.umbaska.Misc.UM2_0;

import java.lang.reflect.Field;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSkullOwnerURL
  extends SimpleExpression<ItemStack>
{
  private Expression<String> url;
  private Expression<ItemStack> item;
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.url = (Expression<String>) args[1];
    this.item = (Expression<ItemStack>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "dyed item";
  }
  

  @Nullable
  protected ItemStack[] get(Event arg0)
  {
    ItemStack itemStack = (ItemStack)this.item.getSingle(arg0);
    String url = (String)this.url.getSingle(arg0);
    
    if (itemStack.getType() == Material.SKULL_ITEM) {
      setURL(itemStack, url);
    }
    return new ItemStack[] { itemStack };
  }
  
  public Class<? extends ItemStack> getReturnType() {
    return ItemStack.class;
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
}
