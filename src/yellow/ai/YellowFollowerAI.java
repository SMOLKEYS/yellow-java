package yellow.ai;

import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.entities.units.entity.YellowUnitEntity;
import yellow.content.YellowUnitTypes;

public class YellowFollowerAI extends AIController{
    
    @Override
    public void updateMovement(){
        
        YellowUnitEntity mogu = null;
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow){
                mogu = ((YellowUnitEntity)e);
            }else{
                mogu = null;
            };
        });
        
        if(mogu != null){
            moveTo(mogu, 12f);
        };
        
        faceMovement();
    }
}
