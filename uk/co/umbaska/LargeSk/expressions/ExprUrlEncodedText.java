package uk.co.umbaska.LargeSk.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Url Encoded Text")
@Syntaxes({"url encoded %string%"})
public class ExprUrlEncodedText
  extends SimpleUmbaskaExpression<String>
{
  private Expression<String> string;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.string = expr[0];
    return true;
  }
  
  @Nullable
  protected String[] get(Event e)
  {
    try
    {
      return new String[] { URLEncoder.encode(((String)this.string.getSingle(e)).toString(), "UTF-8") };
    }
    catch (UnsupportedEncodingException e1)
    {
      e1.printStackTrace();
    }
    return null;
  }
}
