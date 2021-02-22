package uk.co.umbaska.Managers;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLManager
{
  public FileConfiguration newCustomYml(String file)
  {
    File customYml = new File(file);
    if (customYml.exists()) {
      return YamlConfiguration.loadConfiguration(customYml);
    }
    return null;
  }
  

  public Object getSingleYAML(String file, String path)
  {
    FileConfiguration customConfig = newCustomYml(file);
    if (customConfig == null) {
      return null;
    }
    return customConfig.get(path);
  }
  
  public void writeYAML(String file, String path, String value) {
    FileConfiguration customConfig = newCustomYml(file);
    if (customConfig == null) {
      return;
    }
    customConfig.set(value, path);
    saveYAML(file, customConfig);
  }
  
  public void deleteYAML(String file, String path) {
    FileConfiguration customConfig = newCustomYml(file);
    if (customConfig == null) {
      return;
    }
    customConfig.set(path, null);
    saveYAML(file, customConfig);
  }
  
  public void saveYAML(String mainFile, FileConfiguration customConfig) {
    try {
      customConfig.save(mainFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void newFile(String file) {
    File f = new File(file);
    if (f.exists()) {
      return;
    }
    try {
      f.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void delFile(String file)
  {
    File f = new File(file);
    if (f.exists()) {
      f.delete();
    } else {}
  }
}
