package yellow.content;

import mindustry.entities.bullet.*;
import yellow.entities.bullet.*;

public class YellowBullets{
    public static BulletType
    
    standardMissile, standardMissileCaller, placeholderYeetBullet;

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
        
        placeholderYeetBullet = new YeetBulletType(){{
            speed = 4.5f;
            drag = 0.1f;
            lifetime = 240f;
            damage = 40f;
            width = 8f;
            height = 16f;
        }};
    }
}
