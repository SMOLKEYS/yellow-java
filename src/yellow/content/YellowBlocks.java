package yellow.content;

import mindustry.world.*;
import yellow.world.blocks.units.*;

import static yellow.content.YellowUnitTypes.*;

public class YellowBlocks{
    public static Block
    //props for planets
    yellowPropBlock,
    
    //other content
    yellowShrine;
    

    public static void load(){
        yellowPropBlock = new Block("yellow-prop-block"){{
            size = 1;
            solid = false;
        }};
        yellowShrine = new SummoningShrine(yellow){{
            health = 1685;
            size = 1;
            setSummonTime(180f);
            setOneOnly(true);
        }};
    }
}
