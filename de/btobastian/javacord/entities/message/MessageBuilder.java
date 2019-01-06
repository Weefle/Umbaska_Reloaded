package de.btobastian.javacord.entities.message;

import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.User;

public class MessageBuilder
{
  private final StringBuilder strBuilder;
  
  public MessageBuilder()
  {
    this.strBuilder = new StringBuilder();
  }
  
  public MessageBuilder append(String message)
  {
    this.strBuilder.append(message);
    return this;
  }
  
  @Deprecated
  public MessageBuilder appendDecoration(MessageDecoration decoration, String message)
  {
    appendDecoration(message, new MessageDecoration[] { decoration });
    return this;
  }
  
  public MessageBuilder appendDecoration(String message, MessageDecoration... decorations)
  {
    for (MessageDecoration decoration : decorations) {
      this.strBuilder.append(decoration.getPrefix());
    }
    this.strBuilder.append(message);
    for (MessageDecoration decoration : decorations) {
      this.strBuilder.append(decoration.getSuffix());
    }
    return this;
  }
  
  public MessageBuilder appendCode(String language, String message)
  {
    this.strBuilder.append(MessageDecoration.CODE_LONG.getPrefix()).append(language).append("\n").append(message).append(MessageDecoration.CODE_LONG.getSuffix());
    
    return this;
  }
  
  public MessageBuilder appendMention(User user)
  {
    this.strBuilder.append("<@").append(user.getId()).append(">");
    return this;
  }
  
  public MessageBuilder appendUser(User user)
  {
    return appendMention(user);
  }
  
  public MessageBuilder appendNewLine()
  {
    this.strBuilder.append("\n");
    return this;
  }
  
  public MessageBuilder appendChannel(Channel channel)
  {
    this.strBuilder.append("<#").append(channel.getId()).append(">");
    return this;
  }
  
  public StringBuilder getStringBuilder()
  {
    return this.strBuilder;
  }
  
  public String build()
  {
    return this.strBuilder.toString();
  }
  
  public String toString()
  {
    return build();
  }
}
