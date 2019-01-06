package de.btobastian.javacord;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.entities.Application;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Invite;
import de.btobastian.javacord.entities.Region;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.Listener;
import de.btobastian.javacord.utils.ThreadPool;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.concurrent.Future;

public abstract interface DiscordAPI
{
  public abstract void connect(FutureCallback<DiscordAPI> paramFutureCallback);
  
  public abstract void connectBlocking();
  
  public abstract void setEmail(String paramString);
  
  public abstract void setPassword(String paramString);
  
  public abstract void setGame(String paramString);
  
  public abstract String getGame();
  
  public abstract Server getServerById(String paramString);
  
  public abstract Collection<Server> getServers();
  
  public abstract Collection<Channel> getChannels();
  
  public abstract Channel getChannelById(String paramString);
  
  public abstract Collection<VoiceChannel> getVoiceChannels();
  
  public abstract VoiceChannel getVoiceChannelById(String paramString);
  
  public abstract Future<User> getUserById(String paramString);
  
  public abstract User getCachedUserById(String paramString);
  
  public abstract Collection<User> getUsers();
  
  public abstract void registerListener(Listener paramListener);
  
  public abstract Message getMessageById(String paramString);
  
  public abstract ThreadPool getThreadPool();
  
  public abstract void setIdle(boolean paramBoolean);
  
  public abstract boolean isIdle();
  
  public abstract String getToken();
  
  public abstract void setToken(String paramString, boolean paramBoolean);
  
  public abstract boolean checkTokenBlocking(String paramString);
  
  public abstract Future<Server> acceptInvite(String paramString);
  
  public abstract Future<Server> acceptInvite(String paramString, FutureCallback<Server> paramFutureCallback);
  
  public abstract Future<Server> createServer(String paramString);
  
  public abstract Future<Server> createServer(String paramString, FutureCallback<Server> paramFutureCallback);
  
  public abstract Future<Server> createServer(String paramString, Region paramRegion);
  
  public abstract Future<Server> createServer(String paramString, Region paramRegion, FutureCallback<Server> paramFutureCallback);
  
  public abstract Future<Server> createServer(String paramString, BufferedImage paramBufferedImage);
  
  public abstract Future<Server> createServer(String paramString, BufferedImage paramBufferedImage, FutureCallback<Server> paramFutureCallback);
  
  public abstract Future<Server> createServer(String paramString, Region paramRegion, BufferedImage paramBufferedImage);
  
  public abstract Future<Server> createServer(String paramString, Region paramRegion, BufferedImage paramBufferedImage, FutureCallback<Server> paramFutureCallback);
  
  public abstract User getYourself();
  
  public abstract Future<Exception> updateUsername(String paramString);
  
  public abstract Future<Exception> updateEmail(String paramString);
  
  public abstract Future<Exception> updatePassword(String paramString);
  
  public abstract Future<Exception> updateAvatar(BufferedImage paramBufferedImage);
  
  public abstract Future<Exception> updateProfile(String paramString1, String paramString2, String paramString3, BufferedImage paramBufferedImage);
  
  public abstract Future<Invite> parseInvite(String paramString);
  
  public abstract Future<Invite> parseInvite(String paramString, FutureCallback<Invite> paramFutureCallback);
  
  public abstract Future<Exception> deleteInvite(String paramString);
  
  public abstract void setMessageCacheSize(int paramInt);
  
  public abstract int getMessageCacheSize();
  
  public abstract void reconnect(FutureCallback<DiscordAPI> paramFutureCallback);
  
  public abstract void reconnectBlocking();
  
  public abstract void setAutoReconnect(boolean paramBoolean);
  
  public abstract boolean isAutoReconnectEnabled();
  
  public abstract Future<String> convertToBotAccount(String paramString);
  
  public abstract Future<String> convertToBotAccount(String paramString1, String paramString2);
  
  public abstract Future<Collection<Application>> getApplications();
  
  public abstract Future<Collection<Application>> getApplications(FutureCallback<Collection<Application>> paramFutureCallback);
  
  public abstract Future<Application> createApplication(String paramString);
  
  public abstract Future<Application> createApplication(String paramString, FutureCallback<Application> paramFutureCallback);
  
  public abstract Future<Application> getApplication(String paramString);
  
  public abstract Future<Application> getApplication(String paramString, FutureCallback<Application> paramFutureCallback);
  
  public abstract Future<Exception> deleteApplication(String paramString);
  
  public abstract Future<Application> createBot(String paramString);
  
  public abstract Future<Application> createBot(String paramString, FutureCallback<Application> paramFutureCallback);
  
  public abstract Future<Application> createBot(String paramString1, String paramString2);
  
  public abstract Future<Application> createBot(String paramString1, String paramString2, FutureCallback<Application> paramFutureCallback);
}
