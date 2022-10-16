package yellow.content;

import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.bullet.BulletType;
import yellow.entities.bullet.AirstrikeFlare;

public class YellowBullets{
    public static BulletType
    
    standardMissile, standardMissileCaller;

    public static void load(){

        standardMissile = new BombBulletType(){{
            lifetime = 60f;
            speed = 0f;
            width = 32f;
            height = 32f;
            shrinkX = 0.6f;
            shrinkY = 0.6f;
            damage = 350f;
            splashDamage = 310f;
            splashDamageRadius = 88f;
            spin = 7f;
            
            sprite = "yellow-java-standard-missile";
        }};
        
        standardMissileCaller = new AirstrikeFlare(standardMissile);
    }
}
