package de.btobastian.javacord.entities.message;

public enum MessageDecoration
{
  ITALICS("*"),  BOLD("**"),  STRIKEOUT("~~"),  CODE_SIMPLE("`"),  CODE_LONG("```"),  UNDERLINE("__");
  
  private final String prefix;
  private final String suffix;
  
  private MessageDecoration(String prefix)
  {
    this.prefix = prefix;
    this.suffix = new StringBuilder(prefix).reverse().toString();
  }
  
  public String getPrefix()
  {
    return this.prefix;
  }
  
  public String getSuffix()
  {
    return this.suffix;
  }
}
