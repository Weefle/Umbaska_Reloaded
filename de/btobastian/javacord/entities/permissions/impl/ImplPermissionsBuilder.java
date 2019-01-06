package de.btobastian.javacord.entities.permissions.impl;

import de.btobastian.javacord.entities.permissions.PermissionState;
import de.btobastian.javacord.entities.permissions.PermissionType;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.PermissionsBuilder;

public class ImplPermissionsBuilder
  implements PermissionsBuilder
{
  private int allowed = 0;
  private int denied = 0;
  
  public ImplPermissionsBuilder() {}
  
  public ImplPermissionsBuilder(Permissions permissions)
  {
    this.allowed = ((ImplPermissions)permissions).getAllowed();
    this.denied = ((ImplPermissions)permissions).getDenied();
  }
  
  public PermissionsBuilder setState(PermissionType type, PermissionState state)
  {
    switch (state)
    {
    case ALLOWED: 
      this.allowed = type.set(this.allowed, true);
      this.denied = type.set(this.denied, false);
      break;
    case DENIED: 
      this.allowed = type.set(this.allowed, false);
      this.denied = type.set(this.denied, true);
      break;
    case NONE: 
      this.allowed = type.set(this.allowed, false);
      this.denied = type.set(this.denied, false);
    }
    return this;
  }
  
  public PermissionState getState(PermissionType type)
  {
    if (type.isSet(this.allowed)) {
      return PermissionState.ALLOWED;
    }
    if (type.isSet(this.denied)) {
      return PermissionState.DENIED;
    }
    return PermissionState.NONE;
  }
  
  public Permissions build()
  {
    return new ImplPermissions(this.allowed, this.denied);
  }
}
