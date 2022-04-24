package yellow.content;

import arc.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import yellow.entities.bullet.*;

import static arc.Core.*;

public class YellowBullets implements ContentList{
    public static BulletType
    
    standardMissile, standardMissileCaller;
    
    @Override
    public void load(){
        
        
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
        
        standardMissileCaller = new AirstrikeFlare(){{}};
    }
}
