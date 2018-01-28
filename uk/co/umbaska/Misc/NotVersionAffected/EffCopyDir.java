package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.io.FileUtils;
import org.bukkit.event.Event;






public class EffCopyDir
  extends Effect
{
  private Expression<String> file;
  private Expression<String> fileee;
  
  protected void execute(Event event)
  {
    String filee = (String)this.file.getSingle(event);
    String fil = (String)this.fileee.getSingle(event);
    String path = System.getProperty("user.dir");
    File file = new File(path + "/" + filee);
    File fi = new File(path + "/" + fil);
    if ((file.exists()) && (!fi.exists())) {
      try {
        FileUtils.copyDirectory(file, fi);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.print("Can't move non-existant files!");
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Delete file";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.file = expressions[0];
    this.fileee = expressions[1];
    return true;
  }
}
