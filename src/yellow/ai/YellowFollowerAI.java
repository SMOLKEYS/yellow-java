package yellow.ai;

import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.entities.units.entity.YellowUnitEntity;
import yellow.content.YellowUnitTypes;

public class YellowFollowerAI extends AIController{
    
    protected YellowUnitEntity mogu = null;
    
    @Override
    public void updateMovement(){
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow && mogu == null){
                mogu = ((YellowUnitEntity)e);
            }
        });
        
        if(mogu != null){
            moveTo(mogu, 12f);
        };
        
        faceMovement();
    }
}
