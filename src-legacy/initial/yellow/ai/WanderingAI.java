package yellow.ai;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.units.*;

public class WanderingAI extends AIController{

    private static final float minInterval = 60*8, maxInterval = 60*20, wanderingRange = 20;

    protected float currentTime;
    protected Vec2 pos = new Vec2();

    @Override
    public void init(){
        currentTime = Mathf.random(minInterval, maxInterval);
        pos.set(unit.x + (Mathf.range(8, wanderingRange) * 8), unit.y + (Mathf.range(8, wanderingRange) * 8));
    }

    @Override
    public void updateMovement(){
        super.updateMovement();

        currentTime -= Time.delta;

        if(currentTime <= 0){
            currentTime = Mathf.random(minInterval, maxInterval);
            pos.set(unit.x + (Mathf.range(8, wanderingRange) * 8), unit.y + (Mathf.range(8, wanderingRange) * 8));
        }

        moveTo(pos, 2f);
    }
}
