package de.btobastian.javacord.entities.message;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.User;
import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

public abstract interface Message
  extends Comparable<Message>
{
  public abstract String getId();
  
  public abstract String getContent();
  
  public abstract Channel getChannelReceiver();
  
  public abstract User getUserReceiver();
  
  public abstract MessageReceiver getReceiver();
  
  public abstract User getAuthor();
  
  public abstract boolean isPrivateMessage();
  
  public abstract List<User> getMentions();
  
  public abstract boolean isTts();
  
  public abstract Future<Exception> delete();
  
  public abstract Collection<MessageAttachment> getAttachments();
  
  public abstract Future<Message> reply(String paramString);
  
  public abstract Future<Message> reply(String paramString, boolean paramBoolean);
  
  public abstract Future<Message> reply(String paramString, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> reply(String paramString, boolean paramBoolean, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> replyFile(File paramFile);
  
  public abstract Future<Message> replyFile(File paramFile, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> replyFile(InputStream paramInputStream, String paramString);
  
  public abstract Future<Message> replyFile(InputStream paramInputStream, String paramString, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> replyFile(File paramFile, String paramString);
  
  public abstract Future<Message> replyFile(File paramFile, String paramString, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> replyFile(InputStream paramInputStream, String paramString1, String paramString2);
  
  public abstract Future<Message> replyFile(InputStream paramInputStream, String paramString1, String paramString2, FutureCallback<Message> paramFutureCallback);
  
  public abstract Calendar getCreationDate();
  
  public abstract Future<Exception> edit(String paramString);
}
