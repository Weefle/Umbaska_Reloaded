package uk.co.umbaska.Misc.Banners;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;


public class Layer
{
  public Pattern pattern;
  private PatternType patternType;
  private DyeColor dyeColor;
  
  public Layer(DyeColor dye, PatternType type)
  {
    this.dyeColor = dye;
    this.patternType = type;
    this.pattern = new Pattern(dye, type);
  }
  
  public String serialize() {
    return "PATTERNTYPE:%PATTERNTYPE%,DYECOLOR:%DYECOLOR%".replace("%DYECOLOR%", this.dyeColor.getColor().toString()).replace("%PATTERNTYPE%", this.patternType.getIdentifier());
  }
}
