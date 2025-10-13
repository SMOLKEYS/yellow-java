package yellow.phys;

import arc.box2d.*;
import arc.math.geom.*;
import arc.util.*;

public class WorldBox{

    private final Physics core;

    public WorldBox(){
        core = new Physics(new Vec2(), true);
    }

    public Vec2 getGravity(){
        return core.getGravity();
    }

    public void setGravity(float x, float y){
        core.setGravity(getGravity().set(x, y));
    }
}
