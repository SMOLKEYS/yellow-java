package yellow.type;

import arc.struct.*;
import mindustry.type.*;

public class FoodItem extends Item{
    /** If set to true, this food item gives percentage-based health. */
    public boolean healPercent = false;
    
    /** Total health gain when consuming this item. */
    public float healthGain = 40f,
    /** Total health gain by percentage when consuming this item. Must be anywhere between 0-1, with 0 being 0% and 1 being 100%. */
    healthGainPercent = 0f;
    
    /** Possible responses of the specified consumers when consuming this item. */
    public final ObjectMap<UnitType, Seq<String>> responses = new ObjectMap<UnitType, Seq<String>>();
    
    public FoodItem(String name, float health){
        super(name);
        this.health = health;
    }
    
    @Override
    public void setStats(){
        stats.add();
    }
}
