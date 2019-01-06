package de.btobastian.javacord.entities;

import java.util.concurrent.Future;

public abstract interface Application
{
  public abstract String getId();
  
  public abstract String getDescription();
  
  public abstract String[] getRedirectUris();
  
  public abstract String getName();
  
  public abstract String getSecret();
  
  public abstract String getBotToken();
  
  public abstract User getBot();
  
  public abstract Future<Exception> delete();
}
