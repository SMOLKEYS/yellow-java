package yellow.entities.bullet;

import mindustry.gen.*;
import yellow.equality.*;

public class HarshPierceBulletType extends EqualityBulletType{

    public float damageBoost;

    public HarshPierceBulletType(float speed, float damage, float damageBoost){
        super(speed, damage);
        this.damageBoost = damageBoost;
    }

    @Override
    public void handlePierce(Bullet b, float initialHealth, float x, float y){
        super.handlePierce(b, initialHealth, x, y);
        b.damage += damageBoost;
    }
}
