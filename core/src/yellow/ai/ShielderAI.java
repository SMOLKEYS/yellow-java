package yellow.ai;

import arc.math.*;
import arc.math.geom.*;

//relative carbon copy of AssemblerAI for other purposes
public class ShielderAI extends OwnerAI{
    public Vec2 pos = new Vec2();

    @Override
    public void updateMovement(){
        if(owner == null) return;

        if(!pos.isZero()){
            moveTo(owner, 1f);
        }

        unit.lookAt(Angles.angle(owner.x, owner.y, unit.x, unit.y) - 180f); //look behind
    }
}
