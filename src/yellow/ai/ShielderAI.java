package yellow.ai;

import arc.math.*;
import arc.math.geom.*;
import mindustry.entities.units.*;

//relative carbon copy of AssemblerAI for other purposes
public class ShielderAI extends AIController{
    public Vec2 pos = new Vec2();
    public float angle;

    @Override
    public void updateMovement(){
        if(!pos.isZero()){
            moveTo(pos, 1f, 3f);
        }

        if(unit.within(pos, 5f)){
            unit.lookAt(angle);
        }
    }

    public boolean lockedIn(){
        return unit.within(pos, 10f) && Angles.within(unit.rotation, angle, 15f);
    }
}
