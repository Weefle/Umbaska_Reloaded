package uk.co.umbaska.System;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class ExprJavaVersion extends ch.njol.skript.lang.util.SimpleExpression<String>
{
  protected String[] get(Event event)
  {
    String v = getVersion().toString();
    return new String[] { v };
  }
  
  public boolean isSingle() {
    return true;
  }
  
  public Class<? extends String> getReturnType() { return String.class; }
  
  public String toString(Event event, boolean b) {
    return getClass().getName();
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
    return true;
  }
  
  private static Double getVersion() {
    String version = System.getProperty("java.version");
    int pos = 0;int count = 0;
    for (; (pos < version.length()) && (count < 2); pos++) if (version.charAt(pos) == '.') count++;
    return Double.valueOf(Double.parseDouble(version.substring(0, pos)));
  }
}
