package yellow.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.type.Item;
import yellow.content.*;
import yellow.type.FoodItem;

import static yellow.internal.util.YellowUtilsKtKt.seqOf;

public class YellowItems{
    public static Item surgeCandy, megaCopperPack;
    
    public static void load(){
        surgeCandy = new FoodItem("surge-candy"){{
            healing = 170f;
        }};
        
        megaCopperPack = new FoodItem("mega-copper-pack"){{
            healAllAllies = true;
            healUsingPercentage = true;
            healingPercent = 0.07f;
        }};
    }
}
