package yellow.entities.bullet;

import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class BasicDelayBulletType extends BasicBulletType{

    public float trailDelay = -1f;

    public BasicDelayBulletType(){
        super();
    }

    @Override
    public void drawTrail(Bullet b){
        if(b.time >= trailDelay) super.drawTrail(b);
    }

    @Override
    public void updateTrailEffects(Bullet b){
        if(b.time >= trailDelay) super.updateTrailEffects(b);
    }

    @Override
    public void updateTrail(Bullet b){
        if(b.time >= trailDelay) super.updateTrail(b);
    }
}
