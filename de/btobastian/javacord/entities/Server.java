package de.btobastian.javacord.entities;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.concurrent.Future;

public abstract interface Server
{
  public abstract String getId();
  
  public abstract String getName();
  
  public abstract Future<Exception> delete();
  
  public abstract Future<Exception> leave();
  
  public abstract Channel getChannelById(String paramString);
  
  public abstract Collection<Channel> getChannels();
  
  public abstract VoiceChannel getVoiceChannelById(String paramString);
  
  public abstract Collection<VoiceChannel> getVoiceChannels();
  
  public abstract User getMemberById(String paramString);
  
  public abstract Collection<User> getMembers();
  
  public abstract boolean isMember(User paramUser);
  
  public abstract boolean isMember(String paramString);
  
  public abstract Collection<Role> getRoles();
  
  public abstract Role getRoleById(String paramString);
  
  public abstract Future<Channel> createChannel(String paramString);
  
  public abstract Future<Channel> createChannel(String paramString, FutureCallback<Channel> paramFutureCallback);
  
  public abstract Future<VoiceChannel> createVoiceChannel(String paramString);
  
  public abstract Future<VoiceChannel> createVoiceChannel(String paramString, FutureCallback<VoiceChannel> paramFutureCallback);
  
  public abstract Future<Invite[]> getInvites();
  
  public abstract Future<Invite[]> getInvites(FutureCallback<Invite[]> paramFutureCallback);
  
  public abstract Future<Exception> updateRoles(User paramUser, Role[] paramArrayOfRole);
  
  public abstract Future<Exception> banUser(User paramUser);
  
  public abstract Future<Exception> banUser(String paramString);
  
  public abstract Future<Exception> banUser(User paramUser, int paramInt);
  
  public abstract Future<Exception> banUser(String paramString, int paramInt);
  
  public abstract Future<Exception> unbanUser(String paramString);
  
  public abstract Future<User[]> getBans();
  
  public abstract Future<User[]> getBans(FutureCallback<User[]> paramFutureCallback);
  
  public abstract Future<Exception> kickUser(User paramUser);
  
  public abstract Future<Exception> kickUser(String paramString);
  
  public abstract Future<Role> createRole();
  
  public abstract Future<Role> createRole(FutureCallback<Role> paramFutureCallback);
  
  public abstract Future<Exception> updateName(String paramString);
  
  public abstract Future<Exception> updateRegion(Region paramRegion);
  
  public abstract Future<Exception> updateIcon(BufferedImage paramBufferedImage);
  
  public abstract Future<Exception> update(String paramString, Region paramRegion, BufferedImage paramBufferedImage);
  
  public abstract Region getRegion();
  
  public abstract int getMemberCount();
  
  public abstract boolean isLarge();
  
  public abstract Future<Exception> authorizeBot(String paramString);
  
  public abstract Future<Exception> authorizeBot(String paramString, Permissions paramPermissions);
}
