package uk.co.umbaska.Misc.UM2_0;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.event.Event;











public class CondDoesntContain
  extends Condition
{
  private Expression<Object> objectStash;
  private Expression<Object> objectToCheck;
  
  public boolean init(Expression<?>[] expr, int i, Kleenean kl, SkriptParser.ParseResult pr)
  {
    this.objectStash = expr[0];
    this.objectToCheck = expr[1];
    return true;
  }
  
  public String toString(@Nullable Event e, boolean b)
  {
    return "Doesnt Contain";
  }
  
  public boolean check(Event e)
  {
    Object[] objects = this.objectStash.getAll(e);
    Object[] objectsToCheck = this.objectToCheck.getAll(e);
    List<Object> objectList = new ArrayList();
    List<Object> objectList2 = new ArrayList();
    for (Object o : objects) {
      objectList.add(o);
    }
    for (Object o : objectsToCheck) {
      objectList2.add(o);
    }
    return !Collections.disjoint(objectList, objectList2);
  }
}
