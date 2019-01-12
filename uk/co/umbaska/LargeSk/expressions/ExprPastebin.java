package uk.co.umbaska.LargeSk.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.LargeSk.util.Pastebin;

@Syntaxes({"pastebin upload %string% [(named|[with] name) %-string%] [[with] expire date %-string%] [[with] paste (format|language) %-string%]"})
public class ExprPastebin
  extends SimpleExpression<String>
{
  private Expression<String> textToPaste;
  private Expression<String> nameOfPaste;
  private Expression<String> expireDate;
  private Expression<String> pasteFormat;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.textToPaste = (Expression<String>) expr[0];
    if (expr[1] != null) {
      this.nameOfPaste = (Expression<String>) expr[1];
    }
    if (expr[2] != null) {
      this.expireDate = (Expression<String>) expr[2];
    }
    if (expr[3] != null) {
      this.pasteFormat = (Expression<String>) expr[3];
    }
    return true;
  }
  
  @Nullable
  protected String[] get(Event e)
  {
    String ttp = (String)this.textToPaste.getSingle(e);
    String nop;
    if (this.nameOfPaste == null) {
      nop = null;
    } else {
      nop = (String)this.nameOfPaste.getSingle(e);
    }
    String ed;
    if (this.expireDate == null) {
      ed = null;
    } else {
      ed = (String)this.expireDate.getSingle(e);
    }
    String pf;
    if (this.pasteFormat == null) {
      pf = null;
    } else {
      pf = (String)this.pasteFormat.getSingle(e);
    }
    String test = "connection error";
    try
    {
      test = Pastebin.sendPost(ttp, nop, ed, pf);
    }
    catch (Exception e1)
    {
      e1.printStackTrace();
    }
    return new String[] { test };
  }

@Override
public boolean isSingle() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Pastebin upload";
}
}
