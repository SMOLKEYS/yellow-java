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
    yellowAir;
    
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
            
            constructor = UnitEntity::new;
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
            );
        }};
    }
}