package uk.co.umbaska;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import ch.njol.skript.variables.Variables;



public class VariableCache
{
  public HashMap<String, Object> variableValueCache = new HashMap<>();
  
  public List<String> definedGlobalVariables = new ArrayList<>();
  String variablesLocation;
  
  public VariableCache(String variablesLocation)
  {
    this.variablesLocation = variablesLocation;
  }
  
  public synchronized void readVariableFile() {
    String csvFile = this.variablesLocation;
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";
    Integer toRead = Integer.valueOf(Variables.numVariables());
    Bukkit.getLogger().info("Now reading " + toRead + " Skript Variables to add to Cache.");
    Integer readVariableAmount = Integer.valueOf(0);
    if (toRead.intValue() != 0) {
      try
      {
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null)
        {
          String[] variableData = line.split(cvsSplitBy);
          this.variableValueCache.put(variableData[0], Variables.getVariable(variableData[0], new DummyEvent(), false));
         // Integer localInteger1 = readVariableAmount;
         // Integer localInteger2 = readVariableAmount = Integer.valueOf(readVariableAmount.intValue() + 1);
          float divideAmt = 2.0F;
          if (Main.getInstance().getConfig().getBoolean("enable_variable_change_event")) {
            new VariableTracker(line);
          }
          if (readVariableAmount.floatValue() / divideAmt == 0.0F) {
            Bukkit.getLogger().info("Read " + readVariableAmount + " Skript Variables out of " + toRead + " Variables (" + readVariableAmount.intValue() * toRead.intValue() / 100 + "%).");
          }
        }
        Bukkit.getLogger().info("Finished reading " + readVariableAmount + " Skript Variables out of " + toRead + " Variables (" + readVariableAmount.intValue() / toRead.intValue() * 100 + "%).");
        





        if (br != null) {
          try {
            br.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        

        Bukkit.getLogger().info("Finished reading Skript Variables.");
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (br != null) {
          try {
            br.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
          
        }
        
      }
      
    } else {
      Bukkit.getLogger().info("There were no Skript Variables found.");
    }
  }
  
  public void clearNullVariables() {}
}
