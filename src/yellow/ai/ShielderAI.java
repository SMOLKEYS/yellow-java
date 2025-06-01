package yellow.ai;

import arc.math.*;
import arc.math.geom.*;

//relative carbon copy of AssemblerAI for other purposes
public class ShielderAI extends OwnerAI{
    public Vec2 pos = new Vec2();
    public float angle;

    @Override
    public void updateMovement(){
        if(owner == null) return;

        if(!pos.isZero()){
            moveTo(pos, 1f, 0.7f);
        }

        unit.lookAt(Angles.angle(owner.x, owner.y, unit.x, unit.y) - 180f); //look behind
    }

    public boolean lockedIn(){
        return unit.within(pos, 10f) && Angles.within(unit.rotation, angle, 15f);
    }
}
