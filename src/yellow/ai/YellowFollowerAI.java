package yellow.ai;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import yellow.content.YellowUnitTypes;
import yellow.entities.units.entity.YellowUnitEntity;

public class YellowFollowerAI extends AIController{
    
    protected float dist = 80f, wavedist = 20f;
    protected YellowUnitEntity follow;
    protected Building core;
    
    @Override
    public void init(){
        //hurricane
        dist = Mathf.random(80f, 680f);
        wavedist = Mathf.random(20f, 680f);
    }
    
    @Override
    public void updateMovement(){
        
        if(follow != null && follow.dead) follow = null;
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow && follow == null){
                follow = ((YellowUnitEntity)e);
            }
        });
        
        if(follow != null && follow.team == unit.team){
            circle(follow, dist + Mathf.absin(Time.time * 0.05f, 20f, wavedist));
        }else if(core != null){
            circle(core, dist + Mathf.absin(Time.time * 0.05f, 20f, wavedist));
        }else{
            core = unit.team.data().core();
        }
        
        faceMovement();
    
        
    }
}
