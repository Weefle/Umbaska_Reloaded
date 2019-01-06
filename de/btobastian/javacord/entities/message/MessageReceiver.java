package de.btobastian.javacord.entities.message;

import com.google.common.util.concurrent.FutureCallback;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Future;

public abstract interface MessageReceiver
{
  public abstract String getId();
  
  public abstract Future<Message> sendMessage(String paramString);
  
  public abstract Future<Message> sendMessage(String paramString, boolean paramBoolean);
  
  public abstract Future<Message> sendMessage(String paramString, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> sendMessage(String paramString, boolean paramBoolean, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> sendFile(File paramFile);
  
  public abstract Future<Message> sendFile(File paramFile, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> sendFile(InputStream paramInputStream, String paramString);
  
  public abstract Future<Message> sendFile(InputStream paramInputStream, String paramString, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> sendFile(File paramFile, String paramString);
  
  public abstract Future<Message> sendFile(File paramFile, String paramString, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<Message> sendFile(InputStream paramInputStream, String paramString1, String paramString2);
  
  public abstract Future<Message> sendFile(InputStream paramInputStream, String paramString1, String paramString2, FutureCallback<Message> paramFutureCallback);
  
  public abstract Future<MessageHistory> getMessageHistory(int paramInt);
  
  public abstract Future<MessageHistory> getMessageHistory(int paramInt, FutureCallback<MessageHistory> paramFutureCallback);
  
  public abstract Future<MessageHistory> getMessageHistoryBefore(Message paramMessage, int paramInt);
  
  public abstract Future<MessageHistory> getMessageHistoryBefore(Message paramMessage, int paramInt, FutureCallback<MessageHistory> paramFutureCallback);
  
  public abstract Future<MessageHistory> getMessageHistoryBefore(String paramString, int paramInt);
  
  public abstract Future<MessageHistory> getMessageHistoryBefore(String paramString, int paramInt, FutureCallback<MessageHistory> paramFutureCallback);
  
  public abstract Future<MessageHistory> getMessageHistoryAfter(Message paramMessage, int paramInt);
  
  public abstract Future<MessageHistory> getMessageHistoryAfter(Message paramMessage, int paramInt, FutureCallback<MessageHistory> paramFutureCallback);
  
  public abstract Future<MessageHistory> getMessageHistoryAfter(String paramString, int paramInt);
  
  public abstract Future<MessageHistory> getMessageHistoryAfter(String paramString, int paramInt, FutureCallback<MessageHistory> paramFutureCallback);
  
  public abstract void type();
}
