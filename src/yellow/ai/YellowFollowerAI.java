package yellow.ai;

import arc.math.Mathf;
import arc.math.geom.Position;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import yellow.content.YellowUnitTypes;
import yellow.entities.units.entity.YellowUnitEntity;

public class YellowFollowerAI extends AIController{
    
    protected YellowUnitEntity mogu = null;
    protected Unit enem = null;

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
        }else if(enem != null && !enem.dead){
            circle(enem, 45f);
        }else{
            searchEnemy();
        }

        faceMovement();
    }

    private void searchEnemy(){
        if(enem != null) return;
        enem = Groups.unit.find(unor -> unor.team != unit.team && !unor.dead);
    }
}
