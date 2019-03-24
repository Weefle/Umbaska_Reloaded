package uk.co.umbaska.mcMMO;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;




public class ExprLevelOfPlayer
  extends SimpleExpression<Integer>
{
  private Expression<Player> player;
  private Expression<SkillType> skill;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.skill = (Expression<SkillType>) exprs[0];
    this.player = (Expression<Player>) exprs[1];
    
    return true;
  }
  
  protected Integer[] get(Event e)
  {
	  SkillType skill = (SkillType)this.skill.getSingle(e);
    Player player = (Player)this.player.getSingle(e);
    
    return new Integer[] { new Integer(ExperienceAPI.getLevel(player, skill.getName())) };
  }
  


  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "mcMMO level";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
	  SkillType skill = (SkillType)this.skill.getSingle(e);
    Player player = (Player)this.player.getSingle(e);
    if (skill == null)
      return;
    if (player == null)
      return;
    Integer level = (Integer)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ExperienceAPI.setLevel(player, skill.getName(), level.intValue());
    }
    if (mode == Changer.ChangeMode.ADD) {
      ExperienceAPI.setLevel(player, skill.getName(), level.intValue() + ExperienceAPI.getLevel(player, skill.getName()));
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Integer.class });
    if (mode == Changer.ChangeMode.ADD)
      return (Class[])CollectionUtils.array(new Class[] { Integer.class });
    return null;
  }
}
