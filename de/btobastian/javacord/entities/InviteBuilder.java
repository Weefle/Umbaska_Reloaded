package de.btobastian.javacord.entities;

import com.google.common.util.concurrent.FutureCallback;
import java.util.concurrent.Future;

public abstract interface InviteBuilder
{
  public abstract InviteBuilder setMaxUses(int paramInt);
  
  public abstract InviteBuilder setTemporary(boolean paramBoolean);
  
  public abstract InviteBuilder setMaxAge(int paramInt);
  
  public abstract Future<Invite> create();
  
  public abstract Future<Invite> create(FutureCallback<Invite> paramFutureCallback);
}
