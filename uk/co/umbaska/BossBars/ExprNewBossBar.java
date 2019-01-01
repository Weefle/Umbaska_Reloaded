package uk.co.umbaska.BossBars;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprNewBossBar
  extends SimpleExpression<BossBar>
{
  protected BossBar[] get(Event event)
  {
    BossBar bar = Bukkit.createBossBar("Default Title", BarColor.GREEN, BarStyle.SEGMENTED_20, new BarFlag[0]);
    return new BossBar[] { bar };
  }
  
  public boolean isSingle() {
    return true;
  }
  
  public Class<? extends BossBar> getReturnType() {
    return BossBar.class;
  }
  
  public String toString(Event event, boolean b) {
    return "new boss bar";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
    return true;
  }
}
