package uk.co.umbaska.AAC;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;
import me.konsolas.aac.api.AACAPI;
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprViolationLevel
  extends SimpleExpression<Integer>
{
  private Expression<Player> player;
  private Expression<HackType> hack;
  
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.player = expr[0];
    this.hack = expr[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "AAC violation level";
  }
  
  @Nullable
  protected Integer[] get(Event e)
  {
    if (AACAPIProvider.isAPILoaded()) {
      return new Integer[] { Integer.valueOf(AACAPIProvider.getAPI().getViolationLevel((Player)this.player.getSingle(e), (HackType)this.hack.getSingle(e))) };
    }
    return new Integer[] { Integer.valueOf(0) };
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player p = (Player)this.player.getSingle(e);
    HackType h = (HackType)this.hack.getSingle(e);
    Integer i = (Integer)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      AACAPIProvider.getAPI().setViolationLevel(p, h, i.intValue());
    } else if (mode == Changer.ChangeMode.ADD) {
      AACAPIProvider.getAPI().setViolationLevel(p, h, AACAPIProvider.getAPI().getViolationLevel(p, h) - i.intValue());
    } else if (mode == Changer.ChangeMode.REMOVE) {
      AACAPIProvider.getAPI().setViolationLevel(p, h, AACAPIProvider.getAPI().getViolationLevel(p, h) - i.intValue());
    } else if (mode == Changer.ChangeMode.RESET) {
      AACAPIProvider.getAPI().setViolationLevel(p, h, 0);
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.ADD) || (mode == Changer.ChangeMode.RESET) || (mode == Changer.ChangeMode.REMOVE)) {
      return (Class[])CollectionUtils.array(new Class[] { Long.class });
    }
    return null;
  }
  
  public static void register()
  {
    Skript.registerExpression(ExprViolationLevel.class, Integer.class, ExpressionType.PROPERTY, new String[] { "%player%['s][ AAC] [hack[s]|violation[s]|cheat[s]] level of %hacktype%", "[AAC ] %hacktype% [hack[s]|violation[s]|cheat[s]] level of %player%", "%player%'s vl of %hacktype%", "%hacktype% vl of %player%" });
  }
}
