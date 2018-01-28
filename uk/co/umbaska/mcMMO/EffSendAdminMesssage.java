package uk.co.umbaska.mcMMO;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.gmail.nossr50.api.ChatAPI;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;


public class EffSendAdminMesssage
  extends Effect
{
  private Expression<String> sender;
  private Expression<String> message;
  
  protected void execute(Event event)
  {
    String sender = (String)this.sender.getSingle(event);
    String message = (String)this.message.getSingle(event);
    
    if (sender == null) {
      return;
    }
    if (message == null) {
      return;
    }
    ChatAPI.sendAdminChat(Main.plugin, sender, message);
  }
  
  public String toString(Event event, boolean b)
  {
    return "faction claim location";
  }
  


  public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.sender = expressions[1];
    this.message = expressions[0];
    return true;
  }
}
