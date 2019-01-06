package de.btobastian.javacord.entities.permissions.impl;

import de.btobastian.javacord.entities.permissions.PermissionState;
import de.btobastian.javacord.entities.permissions.PermissionType;
import de.btobastian.javacord.entities.permissions.Permissions;

public class ImplPermissions
  implements Permissions
{
  private final int allowed;
  private int denied;
  
  public ImplPermissions(int allow, int deny)
  {
    this.allowed = allow;
    this.denied = deny;
  }
  
  public ImplPermissions(int allow)
  {
    this.allowed = allow;
    for (PermissionType type : PermissionType.values()) {
      if (!type.isSet(allow)) {
        this.denied = type.set(this.denied, true);
      }
    }
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
  
  public int getAllowed()
  {
    return this.allowed;
  }
  
  public int getDenied()
  {
    return this.denied;
  }
  
  public String toString()
  {
    return "Permissions (allowed: " + getAllowed() + ", denied: " + getDenied() + ")";
  }
  
  public boolean equals(Object obj)
  {
    if (!(obj instanceof ImplPermissions)) {
      return false;
    }
    ImplPermissions other = (ImplPermissions)obj;
    return (other.allowed == this.allowed) && (other.denied == this.denied);
  }
}
