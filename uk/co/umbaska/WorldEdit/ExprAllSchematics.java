package uk.co.umbaska.WorldEdit;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;




public class ExprAllSchematics
  extends SimpleExpression<String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return all schematics";
  }
  


  @Nullable
  protected String[] get(Event arg0)
  {
    File dir = new File(Main.schemFolder);
    List<String> schematics = new ArrayList();
    if (!dir.exists())
      dir.mkdirs();
    File[] listOfFiles = dir.listFiles();
    
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        schematics.add(listOfFiles[i].getName());
      } else if (listOfFiles[i].isDirectory()) {
        schematics.add(listOfFiles[i].getName());
      }
    }
    return (String[])schematics.toArray();
  }
}
