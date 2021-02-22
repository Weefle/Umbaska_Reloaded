package uk.co.umbaska.Misc.NotVersionAffected;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;






public class ExprTextUsingProxy
  extends SimpleExpression<String>
{
  private Expression<String> url;
  private Expression<String> proxy;
  
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
    this.url = (Expression<String>) args[0];
    this.proxy = (Expression<String>) args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "placeholder parse %string% as %player%";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    String u = (String)this.url.getSingle(arg0);
    String p = (String)this.proxy.getSingle(arg0);
    
    if (u == null)
      return null;
    if (p == null) {
      return null;
    }
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(p, 80));
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection)new URL(u).openConnection(proxy);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.setRequestProperty("Content-type", "text/xml");
    connection.setRequestProperty("Accept", "text/xml, application/xml");
    try {
      connection.setRequestMethod("POST");
    } catch (ProtocolException e) {
      e.printStackTrace();
    }
    
    return new String[] { null };
  }
}
