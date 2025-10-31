package yellow.entities.bullet;

import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import yellow.content.*;
import yellow.equality.*;

public class ThrowBulletType extends BasicBulletType{
    /** The amount of throwing force to apply to this bullet. */
    public float throwForce = 5f;
    /** The peak minimum speed required for this bullet to be considered "throwable". Usually almost zero in most cases. */
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
        
        if((b.vel().isZero(peakMinSpeed) || speed == peakMinSpeed) && b.fdata > 0f) b.fdata -= Time.delta;
        
        if(b.fdata <= 0f){
            if(throwEffect != null) throwEffect.at(b.x, b.y);
            b.vel().trns(Angles.angle(b.x, b.y, b.aimX, b.aimY), throwForce);
        }
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        if(!EqualityDamage.isEnabled()){
            super.hitEntity(b, entity, health);
            return;
        }

        if(entity instanceof Unit u && EqualityDamage.isEnabled()) EqualityDamage.handle(u, b, health);
    }

    @Override
    public void createSplashDamage(Bullet b, float x, float y){
        if(!EqualityDamage.isEnabled()){
            super.createSplashDamage(b, x, y);
            return;
        }

        if(splashDamageRadius > 0 && !b.absorbed && EqualityDamage.isEnabled()){
            Units.nearbyEnemies(b.team, x, y, splashDamageRadius, en -> EqualityDamage.handle(en, b, en.health));
        }
    }
}
