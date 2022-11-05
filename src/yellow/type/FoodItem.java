package yellow.type;

import arc.struct.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;
import yellow.internal.util.*;
import yellow.world.meta.*;

import static yellow.internal.util.YellowUtilsKtKt.*;

public class FoodItem extends Item{
    /** Instances of this class. */
    public static Seq<FoodItem> instances = seqOf();

    /** Shortened name of this item. */
    public String nameShort = "FoodItem";

    /** Amount of health healed when consumed. Use negative values to inflict damage instead. */
    public float healing = 40f,
    
    /** Amount of health healed when consumed, in percentage. Use negative values to inflict damage instead. */
    healingPercent = 0f;
    
    /** If true, consuming one of this item heals all allied units. */
    public boolean healAllAllies = false,
    
    /** If true, this item heals health in percentage. */
    healUsingPercentage = false;

    /** Responses that the following units may say when consuming this item. */
    public final OrderedMap<UnitType, String[]> responses = new OrderedMap<>();
    
    public FoodItem(String name){
        super(name);
        instances.add(this);
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

    public boolean hasThis(Team team){
        return team.items().get(this) >= 1;
    }
    
    /** Consumes one of this item and heals the unit this was given to, or all units if healAllAllies is true.
     * @param unit The unit this item was given to.
     * @param team The units in the following team that'll be healed. Ignored if this item only heals one unit. If this parameter is null, then the unit inputted in the unit argument will be healed instead. */
    public void consume(Unit unit, Team team){
        if(healAllAllies && team != null){
            Groups.unit.each(un -> {
                if(un.team == team){
                    heal(un);
                }
            });
        }else{
            if(unit != null) heal(unit);
        }


        if(team != null){
            team.items().remove(this, 1);
        }else{
            if(unit != null) unit.team.items().remove(this, 1);
        }
    }

    public String response(Unit unit){
        if(!responses.containsKey(unit.type)) return "...";
        return YellowUtils.random(responses.get(unit.type));
    }
}
