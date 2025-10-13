package yellow.entities.bullet;

import arc.math.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class YeetBulletType extends BasicBulletType{
    /** The amount of throwing force to apply to this bullet. */
    public float throwForce = 5f;
    /** The peak minimum speed required for this bullet to be thrown. Usually zero in most cases. */
    public float peakMinSpeed = 0.001f;
    /** Throw delay. */
    public float throwDelay = 5f;
    /** Effect called on throw. */
    public Effect throwEffect = YellowFx.ghostDespawnMulti;
    
    
    @Override
    public void init(Bullet b){
        super.init(b);
        b.fdata = throwDelay;
    }
    
    @Override
    public void update(Bullet b){
        super.update(b);
        
        if((b.vel().len() <= peakMinSpeed || speed == peakMinSpeed) && b.fdata > 0f) b.fdata -= 1f;
        
        if(b.fdata <= 0f && b.drag != 0f){
            if(throwEffect != null) throwEffect.at(b.x, b.y);
            b.vel().trns(Angles.angle(b.x, b.y, b.aimX, b.aimY), throwForce);
            b.drag(0f);
        }
    }
}
