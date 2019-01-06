package de.btobastian.javacord.entities;

import de.btobastian.javacord.entities.message.MessageReceiver;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import java.util.concurrent.Future;

public abstract interface Channel
  extends MessageReceiver
{
  public abstract String getId();
  
  public abstract String getName();
  
  public abstract String getTopic();
  
  public abstract int getPosition();
  
  public abstract Server getServer();
  
  public abstract Future<Exception> delete();
  
  public abstract InviteBuilder getInviteBuilder();
  
  public abstract Permissions getOverwrittenPermissions(User paramUser);
  
  public abstract Permissions getOverwrittenPermissions(Role paramRole);
  
  public abstract Future<Exception> updateName(String paramString);
  
  public abstract Future<Exception> updateTopic(String paramString);
  
  public abstract Future<Exception> update(String paramString1, String paramString2);
  
  public abstract String getMentionTag();
}
