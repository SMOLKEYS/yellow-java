package yellow.content;

import mindustry.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import yellow.entities.bullet.*;

public class YellowBullets implements ContentList{
    public static BulletType
    
    standardMissile, standardMissileCaller;
    
    @Override
    public void load(){
        
        
        standardMissile = new BombBulletType(){{
            lifetime = 60f;
            speed = 0f;
            shrinkX = 0.5f;
            shrinkY = 0.5f;
            damage = 350f;
            spin = 7f;
            
            sprite = Core.atlas.find("standard-missile");
        }};
        
        standardMissileCaller = new AirstrikeFlare(){{}}; 
    }
}
