package yellow.entities.bullet;

import arc.math.*;
import arc.util.pooling.*;
import mindustry.gen.*;

public class SpinSpearBulletType extends BasicEqualityBulletType{

    public float launchTime;
    public Interp spinInterp = Interp.linear;

    public SpinSpearBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage, bulletSprite);
        this.drawSize = 8*190;
    }

    public SpinSpearBulletType(float speed, float damage){
        super(speed, damage);
        this.drawSize = 8*190;
    }

    public SpinSpearBulletType(){
        super();
        this.drawSize = 8*190;
    }


    @Override
    public void init(Bullet b){
        super.init(b);
        SpearBulletData d = Pools.obtain(SpearBulletData.class, SpearBulletData::new);
        b.lifetime(60*200);
        b.drag(0);
        b.vel().setZero();
        b.rotation(d.startRotation = b.rotation() + Mathf.random(470, 750));
        d.targetRotation = Angles.angle(b.x, b.y, b.aimX, b.aimY);
        b.data(d);
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(b.data() instanceof SpearBulletData s){
            float p = Mathf.clamp(b.time / launchTime);
            b.rotation(Mathf.lerp(s.startRotation, s.targetRotation, spinInterp.apply(p)));

            if(p >= 1f){
                b.time(0f);
                b.vel().trns(s.targetRotation, speed);
                b.lifetime(lifetime);
                b.drag(drag);
                b.data = null;
                s.reset();
            }
        }
    }

    @Override
    public void draw(Bullet b){
        super.draw(b);

        /*
        float alpha = b.lifetime() == 60*200 ? 0f : Mathf.clamp(b.time / launchTime);
        float angle = Angles.angle(b.originX, b.originY, b.aimX, b.aimY);

        Draw.z(Layer.bullet - 1);
        Draw.alpha(alpha);
        Lines.stroke(1.5f);
        Lines.line(b.originX, b.originY, b.aimX + Angles.trnsx(angle, 8*190), b.aimY + Angles.trnsy(angle, 8*190));
        */
    }

    public static class SpearBulletData implements Pool.Poolable{
        public float targetRotation, startRotation;

        public SpearBulletData(){

        }

        @Override
        public void reset(){
            targetRotation = startRotation = 0f;
        }
    }
}
