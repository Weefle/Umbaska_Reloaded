package uk.co.umbaska.UmbAccess;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("UmbAccess - set method")
@Syntaxes({"set method of %class% to %string%"})
public class EffSetMethod
  extends UmbaskaEffect
{
  private Expression<UClass> cla;
  private Expression<String> meth;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.cla = (Expression<UClass>) expressions[0];
    this.meth = (Expression<String>) expressions[1];
    return true;
  }
  
  protected void execute(Event e)
  {
    if ((this.cla == null) || (this.meth == null)) {
      return;
    }
    UClass cl = (UClass)this.cla.getSingle(e);
    String me = (String)this.meth.getSingle(e);
    cl.setUMethod(me);
  }
}
