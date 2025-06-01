package yellow.entities.bullet;

import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import yellow.equality.*;

@SuppressWarnings("unused")
public class ContinuousEqualityFlameBulletType extends ContinuousFlameBulletType{

    public ContinuousEqualityFlameBulletType(float damage){
        super(damage);
    }

    public ContinuousEqualityFlameBulletType(){
        super();
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
