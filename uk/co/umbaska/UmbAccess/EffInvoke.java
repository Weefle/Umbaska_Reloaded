package uk.co.umbaska.UmbAccess;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("UmbAccess - set method")
@Syntaxes({"invoke %class%"})
public class EffInvoke
  extends UmbaskaEffect
{
  private Expression<UClass> cla;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.cla = (Expression<UClass>) expressions[0];
    return true;
  }
  
  protected void execute(Event e)
  {
    if (this.cla == null) {
      return;
    }
    UClass cl = (UClass)this.cla.getSingle(e);
    cl.accessMethod();
  }
}
