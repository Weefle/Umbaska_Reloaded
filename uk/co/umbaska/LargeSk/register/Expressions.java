package uk.co.umbaska.LargeSk.register;

import uk.co.umbaska.AAC.ExprHackDescription;
import uk.co.umbaska.AAC.ExprHackLastMinute;
import uk.co.umbaska.AAC.ExprViolationLevel;
import uk.co.umbaska.LargeSk.expressions.ExprPlayersInWorld;
import uk.co.umbaska.LargeSk.expressions.ExprPlayersInsideVehicle;
import uk.co.umbaska.LargeSk.expressions.ExprPlayersSleeping;

public class Expressions
{
  public void registerGeneral()
  {
    ExprPlayersSleeping.register();
    ExprPlayersInsideVehicle.register();
    ExprPlayersInWorld.register();
  }
  
  public void registerAAC()
  {
    ExprHackDescription.register();
    ExprHackLastMinute.register();
    ExprViolationLevel.register();
  }
}
