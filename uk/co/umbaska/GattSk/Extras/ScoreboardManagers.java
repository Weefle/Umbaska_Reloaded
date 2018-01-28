package uk.co.umbaska.GattSk.Extras;

import ch.njol.skript.Skript;
import java.util.HashMap;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManagers
{
  public static org.bukkit.scoreboard.ScoreboardManager scoreboardManager;
  public static HashMap<String, Scoreboard> boardList = new HashMap();
  
  public static Scoreboard board;
  

  public static void createNewScoreboard(String name)
  {
    if (getBoard(name) == null) {
      scoreboardManager = org.bukkit.Bukkit.getScoreboardManager();
      board = scoreboardManager.getNewScoreboard();
      boardList.put(name, board);
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "Tried to create a Scoreboard that already Exists!");
    }
  }
  
  public static void deleteBoard(String name)
  {
    if (boardList.containsKey(name)) {
      boardList.remove(name);
    }
  }
  
  public static Scoreboard getBoard(String name) {
    if (boardList.containsKey(name)) {
      return (Scoreboard)boardList.get(name);
    }
    
    return null;
  }
  



  public static void setScore(String boardname, String objective, String s, Integer v)
  {
    if (getBoard(boardname) != null) {
      board = (Scoreboard)boardList.get(boardname);
      board.getObjective(objective.toString()).getScore(s).setScore(v.intValue());
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "Tried to set a score within a scoreboard that doesn't exist!");
    }
  }
  

  public static int getScore(String boardname, String obj, String s)
  {
    if (getBoard(boardname) != null) {
      board = (Scoreboard)boardList.get(boardname);
      int score = board.getObjective(obj.toString()).getScore(s).getScore();
      return score;
    }
    
    Skript.error(Skript.SKRIPT_PREFIX + "Tried to get a score within a scoreboard that doesn't exist!");
    return 0;
  }
  
  public static void deleteScore(String boardname, String s)
  {
    if (getBoard(boardname) != null) {
      board = (Scoreboard)boardList.get(boardname);
      board.resetScores(s);
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "Tried to remove a score within a scoreboard that doesn't exist!");
    }
  }
  





  public static void setPlayerScoreboard(Player p, String boardname)
  {
    if (getBoard(boardname) != null) {
      board = (Scoreboard)boardList.get(boardname);
      if (p.getScoreboard() != board) {
        p.setScoreboard(board);
      }
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "Tried to set a scoreboard for a player to a scoreboard that doesn't exist!");
    }
  }
  


  public static void createObjective(String boardname, String name, String objtype)
  {
    if (getBoard(boardname) != null) {
      Scoreboard board = (Scoreboard)boardList.get(boardname);
      board.registerNewObjective(name, objtype);
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "Tried to create an objective for a scoreboard that doesn't exist!");
    }
  }
  
  public static void setObjectiveDisplayName(String boardname, String name, String displayname) {
    if (getBoard(boardname) != null) {
      Scoreboard board = (Scoreboard)boardList.get(boardname);
      board.getObjective(name).setDisplayName(displayname);
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "Tried to create an objective for a scoreboard that doesn't exist!");
    }
  }
  
  public static void unregisterObjective(String boardname, String name) {
    if (getBoard(boardname) != null) {
      Scoreboard board = (Scoreboard)boardList.get(boardname);
      board.getObjective(name).unregister();
    }
    else {
      Skript.error(Skript.SKRIPT_PREFIX + "Tried to unregister an objective for a scoreboard that doesn't exist!");
    }
  }
  
  public static String getObjective(String boardname, String name)
  {
    if (getBoard(boardname) != null) {
      Scoreboard board = (Scoreboard)boardList.get(boardname);
      return board.getObjective(name).toString();
    }
    
    Skript.error(Skript.SKRIPT_PREFIX + "Tried to get an objective for a scoreboard that doesn't exist!");
    
    return null;
  }
  
  public static Objective getObjectiveDisplay(String boardname, String displaySlot)
  {
    if (getBoard(boardname) != null) {
      Scoreboard board = (Scoreboard)boardList.get(boardname);
      return board.getObjective(displaySlot);
    }
    
    Skript.error(Skript.SKRIPT_PREFIX + "Tried to get an objective for a scoreboard that doesn't exist!");
    return null;
  }
  

  public static String getObjectiveType(String boardname, String name)
  {
    if (getBoard(boardname) != null) {
      Scoreboard board = (Scoreboard)boardList.get(boardname);
      String type = board.getObjective(name).getCriteria();
      return type;
    }
    
    Skript.error(Skript.SKRIPT_PREFIX + "Tried to get an objective type for a scoreboard that doesn't exist!");
    return null;
  }
  
  public static Objective get(String id, String criteria, String boardname)
  {
    Scoreboard board = (Scoreboard)boardList.get(boardname);
    Objective o = board.getObjective(id);
    if (o == null) {
      o = board.registerNewObjective(id, criteria);
    }
    return o;
  }
  
  public static void setObjectiveDisplay(String boardname, String objective, String slot) { board = (Scoreboard)boardList.get(boardname);
    if ((slot.equalsIgnoreCase("sidebar")) || (slot.equalsIgnoreCase("side bar")) || (slot.equalsIgnoreCase("side_bar"))) {
      board.getObjective(objective.toString()).setDisplaySlot(DisplaySlot.SIDEBAR);
    } else if ((slot.equalsIgnoreCase("player list")) || (slot.equalsIgnoreCase("playerlist")) || (slot.equalsIgnoreCase("player_list"))) {
      board.getObjective(objective.toString()).setDisplaySlot(DisplaySlot.PLAYER_LIST);
    } else if ((slot.equalsIgnoreCase("below name")) || (slot.equalsIgnoreCase("belowname")) || (slot.equalsIgnoreCase("below_name"))) {
      board.getObjective(objective.toString()).setDisplaySlot(DisplaySlot.BELOW_NAME);
    }
  }
  

  public static void createTeam(String boardname, String teamName)
  {
    board = (Scoreboard)boardList.get(boardname);
    board.registerNewTeam(teamName);
  }
  
  public static String getTeamS(String boardname, String teamName) {
    board = (Scoreboard)boardList.get(boardname);
    return board.getTeam(teamName).toString();
  }
  
  public static Team getTeamActual(String boardname, String teamName) { board = (Scoreboard)boardList.get(boardname);
    return board.getTeam(teamName);
  }
  
  public static void addPlayerToTeam(String boardname, String teamName, OfflinePlayer p)
  {
    Team team = getTeamActual(boardname, teamName);
    team.addPlayer(p);
  }
  
  public static void removePlayerFromTeam(String boardname, String teamName, OfflinePlayer p) {
    Team team = getTeamActual(boardname, teamName);
    team.removePlayer(p);
  }
  
  public static void clearPlayers(String boardname, String teamName) {
    Team team = getTeamActual(boardname, teamName);
    for (OfflinePlayer p : team.getPlayers()) {
      team.removePlayer(p);
    }
  }
  
  public static void setTeamOption(String boardname, String teamName, String option, String stringValue, Boolean boolValue, Integer intValue) {
    Team team = getTeamActual(boardname, teamName);
    if ((option.equalsIgnoreCase("friendlyfire")) || (option.equalsIgnoreCase("friendly fire")) || (option.equalsIgnoreCase("can friendly fire"))) {
      team.setAllowFriendlyFire(boolValue.booleanValue());
    }
    if ((option.equalsIgnoreCase("prefix")) || (option.equalsIgnoreCase("team prefix"))) {
      team.setPrefix(stringValue);
    }
    if ((option.equalsIgnoreCase("suffix")) || (option.equalsIgnoreCase("team suffix"))) {
      team.setSuffix(stringValue);
    }
    if ((option.equalsIgnoreCase("see friendly invisibles")) || (option.equalsIgnoreCase("always see friendlies")) || (option.equalsIgnoreCase("Can See Friendly Invisibles"))) {
      team.setCanSeeFriendlyInvisibles(boolValue.booleanValue());
    }
  }
}
