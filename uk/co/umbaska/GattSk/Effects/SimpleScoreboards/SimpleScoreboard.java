package uk.co.umbaska.GattSk.Effects.SimpleScoreboards;

import ch.njol.skript.Skript;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;



public class SimpleScoreboard
{
  private static HashMap<Scoreboard, HashMap<Integer, String>> ScoreboardTracker = new HashMap();
  private static HashMap<String, Scoreboard> SimpleScoreboards = new HashMap();
  
  private static ScoreboardManager sbm = Bukkit.getScoreboardManager();
  
  public static void debug(String board) {
    Scoreboard targetBoard = (Scoreboard)SimpleScoreboards.get(board);
    Bukkit.broadcastMessage("Scoreboard '" + board + "': " + targetBoard.toString());
    Bukkit.broadcastMessage("Objectives: " + targetBoard.getObjectives().toString());
    Bukkit.broadcastMessage("Title: " + targetBoard.getObjective("SimpleScoreboard").getDisplayName());
    Bukkit.broadcastMessage("HashMap 'ScoreboardTracker': " + ScoreboardTracker.toString());
    Bukkit.broadcastMessage("HashMap 'SimpleScoreboards': " + SimpleScoreboards.toString());
    Bukkit.broadcastMessage("HashMap 'ScoreboardTracker.get': " + ((HashMap)ScoreboardTracker.get(targetBoard)).toString());
  }
  
  public static void newSimpleScoreboard(String name) {
    Scoreboard newBoard = sbm.getNewScoreboard();
    newBoard.registerNewObjective("SimpleScoreboard", "dummy");
    newBoard.getObjective("SimpleScoreboard").setDisplaySlot(DisplaySlot.SIDEBAR);
    SimpleScoreboards.put(name, newBoard);
    ScoreboardManagers.boardList.put(name, newBoard);
    HashMap<Integer, String> newMap = new HashMap();
    newMap.put(Integer.valueOf(1), "&1");
    newMap.put(Integer.valueOf(2), "&2");
    newMap.put(Integer.valueOf(3), "&3");
    newMap.put(Integer.valueOf(4), "&4");
    newMap.put(Integer.valueOf(5), "&5");
    newMap.put(Integer.valueOf(6), "&6");
    newMap.put(Integer.valueOf(7), "&7");
    newMap.put(Integer.valueOf(8), "&8");
    newMap.put(Integer.valueOf(9), "&9");
    newMap.put(Integer.valueOf(10), "&0");
    newMap.put(Integer.valueOf(11), "&a");
    newMap.put(Integer.valueOf(12), "&b");
    newMap.put(Integer.valueOf(13), "&c");
    newMap.put(Integer.valueOf(14), "&d");
    newMap.put(Integer.valueOf(15), "&e");
    ScoreboardTracker.put(newBoard, newMap);
  }
  
  public static void showSimpleBoard(Player targetPlayer, String scoreboardName)
  {
    Scoreboard targetBoard = (Scoreboard)SimpleScoreboards.get(scoreboardName);
    targetPlayer.setScoreboard(targetBoard);
  }
  
  public static void setScoreboardTitle(String scoreboardName, String title) {
    Scoreboard targetBoard = (Scoreboard)SimpleScoreboards.get(scoreboardName);
    targetBoard.getObjective("SimpleScoreboard").setDisplayName(title);
  }
  
  public static void deleteScoreboard(String scoreboardName) {
    if (ScoreboardManagers.boardList.containsKey(scoreboardName)) {
      ScoreboardManagers.boardList.remove(scoreboardName);
    }
    if (SimpleScoreboards.containsKey(scoreboardName)) {
      SimpleScoreboards.remove(scoreboardName);
    }
  }
  
  public static void clearScore(String scoreboardName, Integer slot) {
    Scoreboard targetBoard = (Scoreboard)SimpleScoreboards.get(scoreboardName);
    if (slot.intValue() <= 15) {
      if (slot.intValue() > 0) {
        HashMap<Integer, String> hashMap = (HashMap)ScoreboardTracker.get(targetBoard);
        String score2reset = (String)hashMap.get(slot);
        
        targetBoard.resetScores(score2reset);
        if (slot.intValue() < 10) {
          targetBoard.getObjective("SimpleScoreboard").getScore(ChatColor.translateAlternateColorCodes('&', "&" + slot)).setScore(slot.intValue());
          hashMap.put(slot, "&" + slot);
        }
        else {
          if (slot.intValue() == 10) {
            targetBoard.getObjective("SimpleScoreboard").getScore(ChatColor.translateAlternateColorCodes('&', "&0")).setScore(slot.intValue());
            hashMap.put(slot, "&0");
          }
          if (slot.intValue() == 11) {
            targetBoard.getObjective("SimpleScoreboard").getScore(ChatColor.translateAlternateColorCodes('&', "&a")).setScore(slot.intValue());
            hashMap.put(slot, "&a");
          }
          if (slot.intValue() == 12) {
            targetBoard.getObjective("SimpleScoreboard").getScore(ChatColor.translateAlternateColorCodes('&', "&b")).setScore(slot.intValue());
            hashMap.put(slot, "&b");
          }
          if (slot.intValue() == 13) {
            targetBoard.getObjective("SimpleScoreboard").getScore(ChatColor.translateAlternateColorCodes('&', "&c")).setScore(slot.intValue());
            hashMap.put(slot, "&c");
          }
          if (slot.intValue() == 14) {
            targetBoard.getObjective("SimpleScoreboard").getScore(ChatColor.translateAlternateColorCodes('&', "&d")).setScore(slot.intValue());
            hashMap.put(slot, "&d");
          }
          if (slot.intValue() == 15) {
            targetBoard.getObjective("SimpleScoreboard").getScore(ChatColor.translateAlternateColorCodes('&', "&e")).setScore(slot.intValue());
            hashMap.put(slot, "&e");
          }
        }
        ScoreboardTracker.put(targetBoard, hashMap);
      }
      else {
        Skript.error(Skript.SKRIPT_PREFIX + "SimpleScoreboard Score cannot below 0");
      }
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "SimpleScoreboard Score cannot above 15");
    }
  }
  
  public static String colorString(String s)
  {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
  public static void setScore(String scoreboardName, Integer slot, String value) {
    Scoreboard targetBoard;
    if (SimpleScoreboards.containsKey(scoreboardName)) {
      targetBoard = (Scoreboard)SimpleScoreboards.get(scoreboardName);
    } else {
      return;
    }
    Scoreboard targetBoard;
    if (slot.intValue() <= 15) {
      if (slot.intValue() > 0)
      {

        HashMap<Integer, String> hashMap = (HashMap)ScoreboardTracker.get(targetBoard);
        if (!((String)hashMap.get(slot)).equals(value)) {
          String score2reset = (String)hashMap.get(slot);
          targetBoard.resetScores(score2reset);
          hashMap.remove(slot);
        }
        targetBoard.getObjective("SimpleScoreboard").getScore(value).setScore(slot.intValue());
        hashMap.put(slot, value);
        ScoreboardTracker.put(targetBoard, hashMap);
      }
      else
      {
        Skript.error(Skript.SKRIPT_PREFIX + "SimpleScoreboard Score cannot below 0");
      }
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "SimpleScoreboard Score cannot above 15");
    }
  }
}
