package yellow.ai;

import arc.util.Time;
import arc.math.Mathf;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import yellow.content.YellowUnitTypes;
import yellow.entities.units.entity.YellowUnitEntity;

public class YellowFollowerAI extends AIController{
    
    protected float distus = 80f, bogtus = 20f;
    protected YellowUnitEntity mogu = null;
    protected Building building = null;
    
    @Override
    public void init(){
        //hurricane
        distus = Mathf.random(80f, 680f);
        bogtus = Mathf.random(20f, 110f);
    }
    
    @Override
    public void updateMovement(){
        
        if(mogu != null && mogu.dead) mogu = null;
        
        Groups.unit.each(e -> {
            if(e.type == YellowUnitTypes.yellow && mogu == null){
                mogu = ((YellowUnitEntity)e);
            }
        });
        
        if(mogu != null){
            if(mogu.team == unit.team) circle(mogu, distus + Mathf.absin(Time.time * 0.01f, 20f, bogtus));
        }else if(building != null){
            circle(building, distus + Mathf.absin(Time.time * 0.01f, 20f, bogtus));
        }else{
            building = unit.team.data().core();
        }
        
        faceMovement();
    
        
    }
}
