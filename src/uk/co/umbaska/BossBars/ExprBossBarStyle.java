package uk.co.umbaska.BossBars;

import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;


public class ExprBossBarStyle
  extends SimplePropertyExpression<BossBar, BarStyle>
{
  public BarStyle convert(BossBar bar)
  {
    if (bar == null)
      return null;
    return bar.getStyle();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    BossBar bar = (BossBar)getExpr().getSingle(e);
    if (bar == null)
      return;
    BarStyle b = (BarStyle)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      bar.setStyle(b);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { BarStyle.class });
    return null;
  }
  
  public Class<? extends BarStyle> getReturnType() {
    return BarStyle.class;
  }
  

  protected String getPropertyName()
  {
    return "title of boss bar";
  }
}
