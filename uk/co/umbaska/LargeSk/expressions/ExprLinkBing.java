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

@Name("Search Link - Google")
@Syntaxes({"bing link (of|to) [search] %string%"})
public class ExprLinkBing
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
      return new String[] { "https://www.google.com/search?q=" + URLEncoder.encode(((String)this.search.getSingle(e)).toString(), "UTF-8") };
    }
    catch (UnsupportedEncodingException e1)
    {
      e1.printStackTrace();
    }
    return null;
  }
}
