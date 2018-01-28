package uk.co.umbaska.Misc.Banners;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.PatternType;





public class BannerLayer
{
  public final List<Layer> layers = new ArrayList();
  


  public final BannerLayer addLayer(DyeColor color, PatternType type)
  {
    this.layers.add(new Layer(color, type));
    return this;
  }
  
  public final String serialize() {
    String s = "";
    for (Layer l : this.layers) {
      s = s + l.serialize() + "*END*";
    }
    return s;
  }
}
