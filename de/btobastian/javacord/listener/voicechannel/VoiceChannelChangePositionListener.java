package de.btobastian.javacord.listener.voicechannel;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.listener.Listener;

public abstract interface VoiceChannelChangePositionListener
  extends Listener
{
  public abstract void onVoiceChannelChangePosition(DiscordAPI paramDiscordAPI, VoiceChannel paramVoiceChannel, int paramInt);
}
