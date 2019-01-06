package uk.co.umbaska.Potato;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.Registration.DocsIgnore;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Potato State of Server")
@Syntaxes({"potato state of [(this|the)] server"})
@DocsIgnore
public class ExprPotatoStateOfServer
  extends SimpleUmbaskaExpression<Boolean>
{
  
  public Class<? extends Boolean> getReturnType()
  {
    return Boolean.class;
  }
  
  public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  @Nullable
  protected Boolean[] get(Event e)
  {
    boolean state = PotatoUtil.getPotatoState();
    try
    {
      throw new PotatoException("Too much potatoes!");
    }
    catch (PotatoException ex)
    {
      ex.printStackTrace();
    }
    return new Boolean[] {state};
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Skript.error("Your server will always be a Potato", ErrorQuality.SEMANTIC_ERROR);
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { Boolean.class });
  }
}
