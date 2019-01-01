package uk.co.umbaska;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import ch.njol.skript.variables.Variables;


public class VariableTracker
{
  String variableToCheck;
  Object currentValue;
  BukkitTask task;
  
  public VariableTracker(String variable)
  {
    this.variableToCheck = variable;
    this.currentValue = Variables.getVariable(variable, new DummyEvent(), false);
    this.task = Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable()
    {
      public void run() {
        if (Variables.getVariable(VariableTracker.this.variableToCheck, new DummyEvent(), false) != VariableTracker.this.currentValue) {
          VariableChangeEvent vce = new VariableChangeEvent(VariableTracker.this.variableToCheck, Variables.getVariable(VariableTracker.this.variableToCheck, new DummyEvent(), false));
          if (vce.isCancelled())
            Variables.setVariable(VariableTracker.this.variableToCheck, VariableTracker.this.currentValue, new DummyEvent(), false); } } }, 100L, 100L);
  }
}
