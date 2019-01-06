package uk.co.umbaska.Registration;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.util.coll.CollectionUtils;

@Name("--Change--")
@Syntaxes({"--Change--"})
@Dependency("--Change--")
@DontRegister
public class ExprSimplePropertyExample
  extends SimpleUmbaskaPropertyExpression<Object, String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  @Nullable
  public String convert(Object o)
  {
    return "omg hi";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.REMOVE)) {
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    }
    return null;
  }
}
