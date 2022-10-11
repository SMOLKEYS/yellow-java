package yellow.ai;

import arc.math.Mathf;
import arc.math.geom.Position;
import arc.struct.Seq;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import yellow.content.YellowUnitTypes;
import yellow.entities.units.entity.YellowUnitEntity;

public class YellowFollowerAI extends AIController{
    
    protected YellowUnitEntity mogu = null;
    
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
        }
        
        faceMovement();
    }
}
