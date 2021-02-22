package uk.co.umbaska.ImageManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.bukkit.entity.Player;













public class ImgInChat
{
  public static void ShowImg(Player player, String path, String... contents)
  {
    BufferedImage imageToSend = null;
    try {
      imageToSend = ImageIO.read(new File(path));
    }
    catch (IOException e) {
      e.printStackTrace();
      return;
    }
    new ImageMessage(imageToSend, 8, ImageChar.MEDIUM_SHADE.getChar()).appendText(contents).sendToPlayer(player);
  }
  
  public static void ShowImgFromURL(Player player, String path, String... contents) {
    BufferedImage imageToSend = null;
    try {
      imageToSend = ImageIO.read(new URL(path));
    }
    catch (IOException e) {
      e.printStackTrace();
      return;
    }
    new ImageMessage(imageToSend, 8, ImageChar.MEDIUM_SHADE.getChar()).appendText(contents).sendToPlayer(player);
  }
}
