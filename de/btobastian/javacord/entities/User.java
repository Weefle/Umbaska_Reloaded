package de.btobastian.javacord.entities;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.entities.message.MessageReceiver;
import de.btobastian.javacord.entities.permissions.Role;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.Future;

public abstract interface User
  extends MessageReceiver
{
  public abstract String getId();
  
  public abstract String getName();
  
  public abstract boolean isYourself();
  
  public abstract Future<byte[]> getAvatarAsByteArray();
  
  public abstract Future<byte[]> getAvatarAsByteArray(FutureCallback<byte[]> paramFutureCallback);
  
  public abstract Future<BufferedImage> getAvatar();
  
  public abstract Future<BufferedImage> getAvatar(FutureCallback<BufferedImage> paramFutureCallback);
  
  public abstract URL getAvatarUrl();
  
  public abstract String getAvatarId();
  
  public abstract Collection<Role> getRoles(Server paramServer);
  
  public abstract String getGame();
  
  public abstract String getMentionTag();
  
  public abstract String getDiscriminator();
  
  public abstract boolean isBot();
}
