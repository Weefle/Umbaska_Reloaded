package de.btobastian.javacord.entities.permissions;

import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.Future;

public abstract interface Role
{
  public abstract String getId();
  
  public abstract String getName();
  
  public abstract Server getServer();
  
  public abstract Permissions getPermissions();
  
  public abstract Permissions getOverwrittenPermissions(Channel paramChannel);
  
  public abstract Permissions getOverwrittenPermissions(VoiceChannel paramVoiceChannel);
  
  public abstract List<User> getUsers();
  
  public abstract int getPosition();
  
  public abstract boolean getHoist();
  
  public abstract Color getColor();
  
  public abstract Future<Exception> updatePermissions(Permissions paramPermissions);
  
  public abstract Future<Exception> updateName(String paramString);
  
  public abstract Future<Exception> updateColor(Color paramColor);
  
  public abstract Future<Exception> updateHoist(boolean paramBoolean);
  
  public abstract Future<Exception> update(String paramString, Color paramColor, boolean paramBoolean, Permissions paramPermissions);
  
  public abstract Future<Exception> delete();
  
  public abstract Future<Exception> addUser(User paramUser);
  
  public abstract Future<Exception> removeUser(User paramUser);
}
