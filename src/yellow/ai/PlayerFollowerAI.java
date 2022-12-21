package yellow.ai;

import arc.math.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

public class PlayerFollowerAI extends AIController{
    
    protected Unit target;
    protected Building noTarget;
    protected boolean followingUnit = false;
    protected float targetXR, targetYR;
    protected int time;
    
    @Override
    public void updateMovement(){
        
        time++;
        
        Groups.unit.each(unor -> {
            if(unor.isPlayer() && unor.team == unit.team && !followingUnit){
                target = unor;
                followingUnit = true;
            }else{
                followingUnit = false;
            }
        });
        
        if(target != null){
            
            if(time % 180 == 0){
                targetXR = Mathf.range(120f);
                targetYR = Mathf.range(120f);
            }
            
            if(target.isShooting){
                moveTo(Tmp.v1.set(target.aimX + targetXR, target.aimY + targetYR), 5f);
                unit.lookAt(target.aimX, target.aimY);
                for(WeaponMount mount : unit.mounts){
                    mount.shoot = true;
                }
            }else if(!target.dead){
                circle(target, target.hitSize * 5f);
            }else{
                followingUnit = false;
                target = null;
            }
        }
    }
}
