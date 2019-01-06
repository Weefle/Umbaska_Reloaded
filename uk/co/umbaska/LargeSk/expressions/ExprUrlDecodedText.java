package uk.co.umbaska.LargeSk.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Url Decoded Text")
@Syntaxes({"url decoded %string%"})
public class ExprUrlDecodedText
  extends SimpleUmbaskaExpression<String>
{
  private Expression<String> search;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.search = expr[0];
    return true;
  }
  
  @Nullable
  protected String[] get(Event e)
  {
    try
    {
      return new String[] { URLDecoder.decode(((String)this.search.getSingle(e)).toString(), "UTF-8") };
    }
    catch (UnsupportedEncodingException e1)
    {
      e1.printStackTrace();
    }
    return null;
  }
}
