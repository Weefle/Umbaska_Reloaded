package uk.co.umbaska.mcMMO;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.gmail.nossr50.api.ChatAPI;
import com.gmail.nossr50.datatypes.party.Party;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;


public class EffSendPartyMessage
  extends Effect
{
  private Expression<String> sender;
  private Expression<Party> party;
  private Expression<String> message;
  
  protected void execute(Event event)
  {
    String sender = (String)this.sender.getSingle(event);
    Party party = (Party)this.party.getSingle(event);
    String message = (String)this.message.getSingle(event);
    
    if (sender == null) {
      return;
    }
    if (party == null) {
      return;
    }
    if (message == null) {
      return;
    }
    ChatAPI.sendPartyChat(Main.plugin, sender, party.getName(), message);
  }
  

  public String toString(Event event, boolean b)
  {
    return "faction claim location";
  }
  


  public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.sender = (Expression<String>) expressions[2];
    this.party = (Expression<Party>) expressions[1];
    this.message = (Expression<String>) expressions[0];
    return true;
  }
}
