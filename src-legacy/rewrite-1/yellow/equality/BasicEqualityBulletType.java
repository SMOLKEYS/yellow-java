package yellow.equality;

import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class BasicEqualityBulletType extends BasicBulletType{

    public BasicEqualityBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage, bulletSprite);
    }

    public BasicEqualityBulletType(float speed, float damage){
        super(speed, damage);
    }

    public BasicEqualityBulletType(){
        super();
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        if(!Equality.isEnabled()){
            super.hitEntity(b, entity, health);
            return;
        }

        if(entity instanceof Unit u && Equality.isEnabled()) Equality.handle(u, b, health);
    }

    @Override
    public void createSplashDamage(Bullet b, float x, float y){
        if(!Equality.isEnabled()){
            super.createSplashDamage(b, x, y);
            return;
        }

        if(splashDamageRadius > 0 && !b.absorbed && Equality.isEnabled()){
            Units.nearbyEnemies(b.team, x, y, splashDamageRadius, en -> Equality.handle(en, b, en.health));
        }
    }
}
