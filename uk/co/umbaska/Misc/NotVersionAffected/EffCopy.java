package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;




public class EffCopy
  extends Effect
  implements Listener
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
        Files.copy(file.toPath(), fi.toPath(), new CopyOption[0]);
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
