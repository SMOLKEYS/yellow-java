package yellow.content;

import mindustry.ctype.*;
import mindustry.world.*;
import yellow.ctype.*;
import yellow.world.blocks.units.*;

import static yellow.content.YellowUnitTypes.*;

public class YellowBlocks implements FallbackContentList{
    public static Block
    //props for planets
    yellowPropBlock,
    
    //other content
    yellowShrine;
    
    @Override
    public void load(){
        yellowPropBlock = new Block("yellow-prop-block"){{
            size = 1;
            solid = false;
        }};
        /** TODO */
        yellowShrine = new SummoningShrine(yellow){{
            health = 1685;
            size = 1;
            summonTime = 180f;
        }};
    }
}
