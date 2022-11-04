package yellow.type;

import arc.struct.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.game.*;
import yellow.world.meta.*;

public class FoodItem extends Item{
    /** Amount of health healed when consumed. Use negative values to inflict damage instead. */
    public float healing = 40f,
    
    /** Amount of health healed when consumed, in percentage. Use negative values to inflict damage instead. */
    healingPercent = 0f;
    
    /** If true, consuming one of this item heals all allied units. */
    public boolean healAllAllies = false,
    
    /** If true, this item heals health in percentage. */
    healUsingPercentage = false;
    
    public FoodItem(String name){
        super(name);
    }
    
    @Override
    public void setStats(){
        if(healUsingPercentage){
            stats.addPercent(YellowStats.healingPercent, healingPercent);
        }else{
            stats.add(YellowStats.healing, healing, YellowStats.hp);
        }
        
        stats.add(YellowStats.healAllAllies, healAllAllies);
    }
    
    /** Healing handler. */
    private void heal(Unit unit){
        if(healUsingPercentage){
            unit.healFract(healingPercent);
        }else{
            unit.heal(healing);
        }
    }
    
    /** Consumes one of this item and heals the unit this was given to, or all units if healAllAllies is true. */
    public void consume(Unit unit, Team team){
        if(healAllAllies && team != null){
            Groups.unit.each(un -> {
                if(un.team == team){
                    heal(un);
                }
            });
        }else{
            heal(unit);
        }
        
        team.items().remove(this, 1);
    }
    
    //TODO
    public String response(Unit unit){
        return "...";
    }
}
