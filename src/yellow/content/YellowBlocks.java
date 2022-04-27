package yellow.content;

import mindustry.ctype.*;
import mindustry.world.*;
import yellow.world.blocks.units.*;

import static yellow.content.YellowUnitTypes.*;

public class YellowBlocks implements ContentList{
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
        
        yellowShrine = new SummoningShrine(yellow){{
            health = 1685f;
            size = 4;
            summonTime = 180f;
        }};
    }
}
