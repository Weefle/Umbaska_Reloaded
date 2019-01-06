package uk.co.umbaska.UmbAccess;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("UmbAccess - add param")
@Syntaxes({"add (para|param|parameter) %string% with value %string% to %class%"})
public class EffAddParam
  extends UmbaskaEffect
{
  private Expression<UClass> cla;
  private Expression<String> claz;
  private Expression<String> val;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.claz = (Expression<String>) expressions[0];
    this.val = (Expression<String>) expressions[1];
    this.cla = (Expression<UClass>) expressions[2];
    return true;
  }
  
  protected void execute(Event e)
  {
    if ((this.claz == null) || (this.val == null) || (this.cla == null)) {
      return;
    }
    UClass cl = (UClass)this.cla.getSingle(e);
    String va = (String)this.val.getSingle(e);
    String ca = (String)this.claz.getSingle(e);
    cl.addParam(ca, va);
  }
}
