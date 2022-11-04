package yellow.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.type.Item;
import yellow.content.*;
import yellow.type.FoodItem;

public class YellowItems{
    public static Item surgeCandy, megaCopperPack;
    
    public static void load(){
        surgeCandy = new FoodItem("surge-candy"){{
            healPercent = true;
            healthGainPercent = -0.07f;
            
            unitFavoritesPercentage.put(YellowUnitTypes.yellow, 0.5f);
            
            responses.put(YellowUnitTypes.yellow, Seq.with("Some real candy right here!", "Tingly. I love it!", "Very sweet and electric!", "Thank youuuuuu~!"))
        }};
        
        megaCopperPack = new FoodItem("mega-copper-pack"){{
            healPercent = true;
            healthGainPercent = 0.12f;
        }};
    }
}
