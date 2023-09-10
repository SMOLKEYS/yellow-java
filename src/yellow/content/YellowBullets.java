package yellow.content;

import mindustry.content.*;
import mindustry.entities.bullet.*;
import yellow.entities.bullet.*;

public class YellowBullets{
    public static BulletType
    
    standardMissile, standardMissileCaller, glowOrb;

    public static void load(){

        standardMissile = new BombBulletType(){{
            lifetime = 60f;
            speed = 0f;
            width = height = 32f;
            shrinkX = shrinkY = 0.6f;
            damage = 350f;
            splashDamage = 310f;
            splashDamageRadius = 88f;
            spin = 7f;
            hitEffect = despawnEffect = Fx.explosion;
            
            sprite = "yellow-java-standard-missile";
        }};
        
        standardMissileCaller = new AirstrikeFlare(standardMissile);

        glowOrb = new BasicBulletType(5, 680){{
            lifetime = 120f;
            width = height = 48f;
            shrinkX = shrinkY = 0f;
            hitSize = 35f;
            trailEffect = Fx.fireballsmoke;
            trailChance = 0.2f;
        }};
    }
}
