package yellow.content;

import mindustry.ctype.*;
import mindustry.world.*;

public class YellowBlocks implements ContentList{
    public static Block
    
    yellowPropBlock;
    
    @Override
    public void load(){
        yellowPropBlock = new Block("yellow-prop-block"){{
            size = 1;
            solid = false;
        }};
    }
}
