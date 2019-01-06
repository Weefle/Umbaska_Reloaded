package de.btobastian.javacord.entities.permissions;

public enum PermissionType
{
  CREATE_INSTANT_INVITE(0),  KICK_MEMBERS(1),  BAN_MEMBERS(2),  MANAGE_ROLES(3),  MANAGE_PERMISSIONS(3),  MANAGE_CHANNELS(4),  MANAGE_CHANNEL(4),  MANAGE_SERVER(5),  READ_MESSAGES(10),  SEND_MESSAGES(11),  SEND_TTS_MESSAGES(12),  MANAGE_MESSAGES(13),  EMBED_LINKS(14),  ATTACH_FILE(15),  READ_MESSAGE_HISTORY(16),  MENTION_EVERYONE(17),  VOICE_CONNECT(20),  VOICE_SPEAK(21),  VOICE_MUTE_MEMBERS(22),  VOICE_DEAFEN_MEMBERS(23),  VOICE_MOVE_MEMBERS(24),  VOICE_USE_VAD(25);
  
  private final int offset;
  
  private PermissionType(int offset)
  {
    this.offset = offset;
  }
  
  public int getOffset()
  {
    return this.offset;
  }
  
  public boolean isSet(int i)
  {
    return (i & 1 << this.offset) != 0;
  }
  
  public int set(int i, boolean set)
  {
    if ((!set) && (isSet(i))) {
      i = (int)(i - Math.pow(2.0D, getOffset()));
    } else if ((set) && (!isSet(i))) {
      i = (int)(i + Math.pow(2.0D, getOffset()));
    }
    return i;
  }
}
