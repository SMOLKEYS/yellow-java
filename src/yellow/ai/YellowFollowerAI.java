package yellow.ai;

import arc.Nullable;
import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.entities.units.entity.YellowUnitEntity;
import yellow.content.YellowUnitTypes;

public class YellowFollowerAI extends AIController{
    
    @Override
    public void updateMovement(){
        
        @Nullable YellowUnitEntity mogu = null;
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow){
                mogu = e;
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
