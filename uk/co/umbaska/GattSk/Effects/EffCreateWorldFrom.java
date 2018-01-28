package uk.co.umbaska.GattSk.Effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.GattSk.Extras.WorldManagers;



public class EffCreateWorldFrom
  extends Effect
{
  private Expression<String> worldname;
  private Expression<String> foldername;
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.worldname = args[0];
    this.foldername = args[1];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "create new world";
  }
  
  protected void execute(Event arg0)
  {
    String worldname = (String)this.worldname.getSingle(arg0);
    String foldername = (String)this.foldername.getSingle(arg0);
    if ((worldname == null) || (foldername == null)) {
      return;
    }
    WorldManagers.createWorldFrom(worldname, foldername);
  }
}
