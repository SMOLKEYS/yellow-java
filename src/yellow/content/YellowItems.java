package yellow.content;

import arc.math.*;
import mindustry.type.*;
import yellow.type.*;

public class YellowItems{
    public static Item surgeCandy, megaCopperPack, lesserMegaCopperPack, carbideChips, trueCarbideChips, stockItem;
    
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
        
        carbideChips = new FoodItem("carbide-chips"){{
            nameShort = "CarbChps";
            healUsingPercentage = true;
            healingPercent = 1.01f;
        }
            
            @Override
            public void update(){
                if(Mathf.chance(0.07)) this.healingPercent = Mathf.random(1.01f, 200000f);
            }
        };
        
        stockItem = new FoodItem("stock-item"){{
            nameShort = "StckItem";
            healingPercent = healing = 0f;
            internalFood = true;
            hidden = true;
        }};
    }
}
