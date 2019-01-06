package de.btobastian.javacord.entities;

public enum Region
{
  AMSTERDAM("amsterdam", "Amsterdam"),  FRANKFURT("frankfurt", "Frankfurt"),  LONDON("london", "London"),  SINGAPORE("singapore", "Singapore"),  SYDNEY("sydney", "Sydney"),  US_CENTRAL("us-central", "US Central"),  US_EAST("us-east", "US East"),  US_SOUTH("us-south", "US South"),  US_WEST("us-west", "US West"),  UNKNOWN("us-west", "Unknown");
  
  private final String key;
  private final String name;
  
  private Region(String key, String name)
  {
    this.key = key;
    this.name = name;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public static Region getRegionByKey(String key)
  {
    for (Region region : ) {
      if ((region.getKey().equalsIgnoreCase(key)) && (region != UNKNOWN)) {
        return region;
      }
    }
    return UNKNOWN;
  }
}
