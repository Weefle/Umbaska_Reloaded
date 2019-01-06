package de.btobastian.javacord.listener.voicechannel;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.listener.Listener;

public abstract interface VoiceChannelChangeNameListener
  extends Listener
{
  public abstract void onVoiceChannelChangeName(DiscordAPI paramDiscordAPI, VoiceChannel paramVoiceChannel, String paramString);
}
