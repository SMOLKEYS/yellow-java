package yellow.ai;

import arc.math.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.blocks.storage.CoreBlock.*;

public class YellowOrbiterAI extends AIController{
    
    protected float dist = 80f, wavedist = 20f;
    protected YellowUnitEntity follow;
    protected CoreBuild core;
    
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
                YellowUnitEntity defo = (YellowUnitEntity) e;
                if(defo.team == unit.team) follow = defo;
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
