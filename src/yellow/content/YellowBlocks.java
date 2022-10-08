package yellow.content;

import mindustry.world.Block;
import yellow.ctype.FallbackContentList;
import yellow.world.blocks.units.SummoningShrine;

import static yellow.content.YellowUnitTypes.yellow;

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
        yellowShrine = new SummoningShrine(yellow){{
            health = 1685;
            size = 1;
            setSummonTime(180f); //kotlin what
        }};
    }
}
