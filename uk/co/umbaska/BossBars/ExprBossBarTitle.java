package uk.co.umbaska.BossBars;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;



public class ExprBossBarTitle
  extends SimplePropertyExpression<BossBar, String>
{
  public String convert(BossBar bar)
  {
    if (bar == null)
      return null;
    return bar.getTitle();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    BossBar bar = (BossBar)getExpr().getSingle(e);
    if (bar == null)
      return;
    String b = (String)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      bar.setTitle(b);
    }
    if (mode == Changer.ChangeMode.RESET) {
      bar.setTitle("Default Title");
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    if (mode == Changer.ChangeMode.RESET)
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    return null;
  }
  
  public Class<? extends String> getReturnType() {
    return String.class;
  }
  

  protected String getPropertyName()
  {
    return "title of boss bar";
  }
}
