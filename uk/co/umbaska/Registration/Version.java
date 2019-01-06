package uk.co.umbaska.Registration;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

public enum Version
{
  V1_7_R1,  V1_7_R2,  V1_7_R3,  V1_8_R1,  V1_8_R3,  V1_9_R1,  V1_9_R2;
  
  public static Version serverVersion = null;
  
  private Version() {}
  
  public String toString()
  {
    return name().toLowerCase();
  }
  
  public static Version parse(String toParse)
  {
    for (Version version : values()) {
      if (StringUtils.containsIgnoreCase(version.name(), toParse)) {
        return version;
      }
    }
    return null;
  }
  
  public int getId()
  {
    int i = 1;
    for (Version v : values())
    {
      if (this == v) {
        return i;
      }
      i++;
    }
    return 0;
  }
  
  public static int getId(Version version)
  {
    int i = 1;
    for (Version v : values())
    {
      if (version == v) {
        return i;
      }
      i++;
    }
    return 0;
  }
  
  public static boolean isAtLeast(AtLeast atLeast)
  {
    return atLeast.value().getId() >= serverVersion.getId();
  }
  
  public static Version getById(int id)
  {
    for (Version version : values()) {
      if (version.getId() == id) {
        return version;
      }
    }
    return null;
  }
  
  public static void checkServerVersion()
  {
    String fullVersion = Bukkit.getServer().getClass().getPackage().getName();
    String versionCode = fullVersion.substring(fullVersion.lastIndexOf('.') + 1);
    serverVersion = parse(versionCode);
  }
}
