package yellow.ai;

import arc.math.Mathf;
import arc.math.geom.Position;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import yellow.content.YellowUnitTypes;
import yellow.entities.units.entity.YellowUnitEntity;

public class YellowFollowerAI extends AIController{
    
    protected YellowUnitEntity mogu = null;
    protected Building locn = null;
    @Override
    public void updateMovement(){
        
        if(mogu != null && mogu.dead) mogu = null;
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow && mogu == null){
                mogu = ((YellowUnitEntity)e);
            }
        });
        
        if(mogu != null){
            if(mogu.team == unit.team) moveTo(mogu, 20f);
        }else if(locn == null){
            locn = unit.team.data().core();
        }else{
            moveTo(new Position(){
                @Override
                public float getX(){
                    return locn.getX() + Mathf.range(50f);
                }

                @Override
                public float getY(){
                    return locn.getY() + Mathf.range(50f);
                }
            }, 1f);
        }

        faceMovement();
    }

    @Override
    public void moveTo(Position target, float circleLength){
        if(Mathf.chance(0.130)) super.moveTo(target, circleLength);
    }
}
