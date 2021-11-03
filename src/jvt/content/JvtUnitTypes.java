package jvt.content;

//a
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.ai.types.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class JvtUnitTypes implements ContentList{
    public static UnitType
    
    //yellow units
    yellowAir, yellowGround;
    
    @Override
    public void load(){
        
        yellowAir = new UnitType("yellowAir"){{
            flying = true;
            health = 2147483647f;
            armor = 2147483647f;
            speed = 3f;
            accel = 0.08f;
            drag = 0.01f;
            range = 240f;
            maxRange = 240f;
            
            constructor = UnitEntity::create;
            defaultController = DefenderAI::new;
            
            weapons.add(
                new Weapon("meltdown-shotgun"){{
                    reload = 60f;
                    x = 56f;
                    mirror = true;
                    shots = 15;
                    inaccuracy = 30f;
                    bullet = new ContinuousLaserBulletType(){{
                        damage = 100f;
                        width = 8f;
                        length = 240f;
                        lifetime = 60f;
                    }};
                }}
                /*new Weapon("bullethell"){{}}*/
            );
        }};
        
        yellowGround = new UnitType("yellowGround"){{
            health = 2147483647f;
            armor = 2147483647f;
            speed = 1f;
            range = 400f;
            maxRange = 400f;
            
            constructor = MechUnit::create;
            
            weapons.add(
                new Weapon("anti-moth-spray"){{
                    reload = 100f;
                    x = 3f;
                    mirror = true;
                    shots = 300;
                    burstSpacing = 2f;
                    inaccuracy = 15f;
                    bullet = new BasicBulletType(){{
                        damage = 1f;
                        lifetime = 60f;
                        speed = 4f;
                        width = 8f;
                        height = 8f;
                        knockback = 5f;
                    }};
                }}
            );
        }};
    }
}