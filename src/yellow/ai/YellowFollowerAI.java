package yellow.ai;

import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.entities.units.entity.YellowUnitEntity;
import yellow.content.YellowUnitTypes;

public class YellowFollowerAI extends AIController{
    
    protected YellowUnitEntity mogu = null;
    
    @Override
    public void updateMovement(){
        
        if(mogu != null && mogu.dead) mogu = null;
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow && mogu == null){
                mogu = ((YellowUnitEntity)e);
            };
        });
        
        if(mogu != null){
            if(mogu.team == unit.team) moveTo(mogu, 12f);
        };
        
        faceMovement();
    }
}
