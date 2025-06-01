package yellow.entities.bullet;

import arc.util.*;
import mindustry.gen.*;

public class RotatingContinuousLaserBulletType extends ContinuousExtendingLaserBulletType{

    /** Speed that the bullet rotates at. */
    public float rotateSpeed = 1f;

    public RotatingContinuousLaserBulletType(float damage){
        super(damage);
    }

    public RotatingContinuousLaserBulletType(){
        super();
    }

    @Override
    public void update(Bullet b){
        super.update(b);
        b.rotation(b.rotation() + (Time.delta * rotateSpeed));
    }
}