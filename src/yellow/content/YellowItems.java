package yellow.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.type.Item;
import yellow.content.*;
import yellow.type.FoodItem;

import static yellow.internal.util.YellowUtilsKtKt.seqOf;

public class YellowItems{
    public static Item surgeCandy, megaCopperPack, lesserMegaCopperPack;
    
    public static void load(){
        surgeCandy = new FoodItem("surge-candy"){{
            nameShort = "SurgCndy";
            healing = 170f;
        }};
        
        megaCopperPack = new FoodItem("mega-copper-pack"){{
            nameShort = "MCoprPck";
            healAllAllies = true;
            healUsingPercentage = true;
            healingPercent = 0.05f;
        }};

        lesserMegaCopperPack = new FoodItem("lesser-mega-copper-pack"){{
            nameShort = "LCoprPck";
            healUsingPercentage = true;
            healingPercent = 0.05f;
        }};
    }
}
