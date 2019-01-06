package uk.co.umbaska.Discord;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.btobastian.javacord.DiscordAPI;
import uk.co.umbaska.Registration.Discord;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Discord - New Connection")
@Syntaxes({"new d[is[cord]][ ][account ]connection (with|from) ([e]mail|login) %string% and (pw|pass[word] %string%"})
@Discord
public class ExprNewDiscordConnection
  extends SimpleUmbaskaExpression<DiscordAPI>
{
  Expression<String> email;
  Expression<String> password;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.email = (Expression<String>) exprs[0];
    this.password = (Expression<String>) exprs[1];
    return true;
  }
  
  protected DiscordAPI[] get(Event e)
  {
    String email = (String)this.email.getSingle(e);
    String password = (String)this.password.getSingle(e);
    if ((email == null) || (password == null)) {
      return null;
    }
    return new DiscordAPI[] { DiscordUtil.connect(email, password) };
  }
  
  public Class<? extends DiscordAPI> getReturnType()
  {
    return DiscordAPI.class;
  }
}
