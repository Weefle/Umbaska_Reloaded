package uk.co.umbaska.UmbAccess;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaPropertyExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("UmbAccess - new uclass")
@Syntaxes({"new class %string%"})
public class ExprUClass
  extends SimpleUmbaskaPropertyExpression<String, UClass>
{
  public Class<? extends UClass> getReturnType()
  {
    return UClass.class;
  }
  
  @Nullable
  public UClass convert(String o)
  {
    return new UClass(o);
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    String ent = (String)getExpr().getSingle(e);
    if (ent == null) {}
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.REMOVE)) {
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    }
    return null;
  }
}
