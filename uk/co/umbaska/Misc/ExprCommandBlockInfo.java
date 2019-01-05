package uk.co.umbaska.Misc;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;







public class ExprCommandBlockInfo
  extends SimpleExpression<String>
{
  private Expression<Block> block;
  int matchType;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.matchType = arg1;
    this.block = (Expression<Block>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return owner of plot";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    Block b = (Block)this.block.getSingle(arg0);
    
    if (b == null)
      return null;
    if (b.getType() != Material.COMMAND) {
      return null;
    }
    CommandBlock cmb = (CommandBlock)b.getState();
    if (this.matchType == 0) {
      return new String[] { cmb.getCommand() };
    }
    return new String[] { cmb.getName() };
  }
}
