package yellow.content;

import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.bullet.BulletType;
import yellow.ctype.FallbackContentList;
import yellow.entities.bullet.AirstrikeFlare;

import static arc.Core.atlas;

public class YellowBullets implements FallbackContentList{
    public static BulletType
    
    standardMissile, standardMissileCaller;
    
    @Override
    public void load(){
        
        /** TODO */
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
            
            sprite = "standard-missile";
            frontRegion = atlas.find("standard-missile");
            backRegion = atlas.find("standard-missile");
        }};
        
        standardMissileCaller = new AirstrikeFlare(standardMissile){{}};
    }
}
