package uk.co.umbaska.Misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.event.Event;












public class EffSetCommandBlockInfo
  extends Effect
{
  private Expression<Block> block;
  private Expression<String> input;
  int matchType;
  
  protected void execute(Event event)
  {
    Block b = (Block)this.block.getSingle(event);
    String i = (String)this.input.getSingle(event);
    if (b == null)
      return;
    if (i == null) {
      return;
    }
    CommandBlock cmb = (CommandBlock)b.getState();
    if (this.matchType == 0) {
      cmb.setCommand(i);
      cmb.update();
    } else {
      cmb.setName(i);
      cmb.update();
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "set command block info";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.matchType = i;
    this.block = expressions[0];
    this.input = expressions[1];
    return true;
  }
}
