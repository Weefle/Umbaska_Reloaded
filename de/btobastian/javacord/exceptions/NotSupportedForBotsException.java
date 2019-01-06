package de.btobastian.javacord.exceptions;

public class NotSupportedForBotsException
  extends IllegalStateException
{
  public NotSupportedForBotsException()
  {
    super("Bots are not able to use this method!");
  }
  
  public NotSupportedForBotsException(String message)
  {
    super(message);
  }
}
