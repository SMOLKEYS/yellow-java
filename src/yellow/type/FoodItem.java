package yellow.type;

import arc.struct.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.game.*;

public class FoodItem extends Item{
    private static boolean healedAnything = false;
    
    
    /** If set to true, this food item gives health in percentage. */
    public boolean healPercent = false,
    
    /** If set to true, consuming this food item heals all units in the player's team. */
    healAll = false,
    
    /** Determines whether this item can only heal whitelisted units, or any unit. */
    useWhitelist = false;
    
    /** Total health gain when consuming this item. */
    public float healthGain = 40f,
    
    /** Total health gain by percentage when consuming this item. Must be anywhere between 0-1, with 0 being 0% and 1 being 100%. */
    healthGainPercent = 0f;
    
    /** Possible responses of the specified consumers when consuming this item. */
    public final ObjectMap<UnitType, Seq<String>> responses = new ObjectMap<UnitType, Seq<String>>();
    
    /** A list of units that can gain more health than other units consuming this item. Can be set to a negative value to make specific units take damage consuming this item. */
    public final ObjectMap<UnitType, Float> unitFavorites = new ObjectMap<UnitType, Float>();
    
    /** unitFavorites, but with percentage. Does not require healAll to be true. */
    public final ObjectMap<UnitType, Float> unitFavoritesPercentage = new ObjectMap<UnitType, Float>();
    
    /** If useWhitelist is true, then every single unit cannot consume this item, except for units in this whitelist.
      * If healAll is true, then only the units in this list will be healed.
      */
    public final Seq<UnitType> whitelist = new Seq<UnitType>();
    
    public FoodItem(String name){
        super(name);
    }
    
    @Override
    public void setStats(){
        //TODO
    }
    
    private void heal(Unit unit){
        if(unit.health == unit.type.health) return;
        if(healPercent){
            unit.healFract(healthGainPercent);
        }else{
            unit.heal(healthGain);
        }
        healedAnything = true;
    }
    
    private void healFavorite(Unit unit){
        if(unit.health == unit.type.health) return;
        if(unitFavorites.containsKey(unit.type)) unit.heal(unitFavorites.get(unit.type));
        if(unitFavoritesPercentage.containsKey(unit.type)) unit.heal(unitFavoritesPercentage.get(unit.type));
        healedAnything = true;
    }
    
    public boolean hasThis(Team team){
        return team.items().get(this) <= 1;
    }
    
    public void consume(Team team, Unit unit){
        if(!hasThis(team)) return;
        Groups.unit.each(t -> {
            if(t.team != team){
                if(unitFavorites.containsKey(t.type) || unitFavoritesPercentage.containsKey(t.type)) return;
                if(healAll){
                    if(useWhitelist){
                        if(whitelist.contains(t.type)) heal(t);
                    }else{
                        heal(t);
                    }
                }else{
                    heal(unit);
                }
            }
        });
        
        //other ones
        Groups.unit.each(t -> {
            if(t.team == team){
                if(healAll){
                    healFavorite(t);
                }else{
                    healFavorite(unit);
                }
            }
        });
        
        if(healedAnything){
            team.items().remove(this, 1);
            healedAnything = false;
        }
    }
    
    public String response(UnitType type){
        if(responses.get(type) == null) return "...";
        return responses.get(type).random();
    }
}
