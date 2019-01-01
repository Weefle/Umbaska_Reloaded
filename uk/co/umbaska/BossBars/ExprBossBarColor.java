package uk.co.umbaska.BossBars;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprBossBarColor
  extends SimplePropertyExpression<BossBar, BarColor>
{
  public BarColor convert(BossBar bar)
  {
    if (bar == null)
      return null;
    return bar.getColor();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    BossBar bar = (BossBar)getExpr().getSingle(e);
    if (bar == null)
      return;
    BarColor b = (BarColor)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      bar.setColor(b);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { BarColor.class });
    return null;
  }
  
  public Class<? extends BarColor> getReturnType() {
    return BarColor.class;
  }
  

  protected String getPropertyName()
  {
    return "title of boss bar";
  }
}
