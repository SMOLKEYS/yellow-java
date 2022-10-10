package yellow.ai;

import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.content.YellowUnitTypes;

public class YellowFollowerAI extends AIController{
    
    @Override
    public void updateMovement(){
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow){
                unit = e;
            }else{
                unit = null;
            };
        });
        
        if(unit != null){
            moveTo(unit, 12f);
        };
        
        faceMovement();
    }
}
