package yellow.content;

import mindustry.world.*;
import yellow.world.blocks.units.*;

import static yellow.content.YellowUnitTypes.*;

public class YellowBlocks{

    public static Block
    yellowShrine;
    

    public static void load(){
        yellowShrine = new SummoningShrine(yellow){{
            health = 100;
            size = 1;
            setSummonTime(180f);
            setOneOnly(true);
        }};
    }
}
