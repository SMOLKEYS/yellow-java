package yellow.entities.bullet;

import arc.math.*;
import mindustry.gen.*;

public class SimpleContinuousSweepLaserBulletType extends ContinuousExtendingLaserBulletType{

    /** Initial offset rotation added to the bullet when spawning. */
    public float initialOffsetRotation = 0f;
    /** Target offset rotation. Usually a full 0-360 degree sweep. */
    public float targetOffsetRotation = 360f;
    /** Time needed to reach the target rotation, in ticks. */
    public float sweepTime;
    /** Interpolation used during the time needed to reach the target rotation. */
    public Interp interp = Interp.pow5In;

    public SimpleContinuousSweepLaserBulletType(float damage){
        super(damage);
    }

    public SimpleContinuousSweepLaserBulletType(){
        super();
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        b.rotation(b.rotation() + initialOffsetRotation);
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        float target = b.rotation() + (targetOffsetRotation * Mathf.clamp(b.time / sweepTime));

        b.rotation(target);
    }
}
