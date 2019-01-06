package de.btobastian.javacord.entities;

import com.google.common.util.concurrent.FutureCallback;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.Future;

public abstract interface Invite
{
  public abstract String getCode();
  
  public abstract URL getInviteUrl();
  
  public abstract String getServerId();
  
  public abstract String getServerName();
  
  public abstract Server getServer();
  
  public abstract String getChannelId();
  
  public abstract String getChannelName();
  
  public abstract Channel getChannel();
  
  public abstract VoiceChannel getVoiceChannel();
  
  public abstract boolean isVoiceChannel();
  
  public abstract int getMaxAge();
  
  public abstract boolean isRevoked();
  
  public abstract Calendar getCreationDate();
  
  public abstract int getUses();
  
  public abstract int getMaxUses();
  
  public abstract boolean isTemporary();
  
  public abstract User getCreator();
  
  public abstract Future<Server> acceptInvite();
  
  public abstract Future<Server> acceptInvite(FutureCallback<Server> paramFutureCallback);
  
  public abstract Future<Exception> delete();
}
