package yellow.ai;

import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import yellow.content.YellowUnitTypes;
import yellow.entities.units.entity.YellowUnitEntity;

public class YellowFollowerAI extends AIController{
    
    protected YellowUnitEntity mogu = null;
    protected Building building = null;
    
    @Override
    public void updateMovement(){
        
        if(mogu != null && mogu.dead) mogu = null;
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow && mogu == null){
                mogu = ((YellowUnitEntity)e);
            }
        });
        
        if(mogu != null){
            if(mogu.team == unit.team) circle(mogu, 120f);
        }else if(building != null){
            circle(building, 80f);
        }else{
            building = unit.team.data().core();
        }
        
        faceMovement();
    }
}
