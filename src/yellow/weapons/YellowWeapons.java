package yellow.weapons;

import arc.*;
import arc.flabel.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

public class YellowWeapons{
    public static Weapon
    
    meltdownBurstAttack, bullethellAttack;
    
    public static void init(){
        meltdownBurstAttack = new Weapon("meltdown-burst"){{
            reload = 60f;
            x = 56f;
            mirror = true;
            shots = 15;
            shotDelay = 5f;
            shootSound = Sounds.explosionbig;
            inaccuracy = 30f;
            bullet = new ContinuousLaserBulletType(){{
                damage = 100f;
                width = 8f;
                length = 240f;
                lifetime = 60f;
            }};
        }};
        
        bullethellAttack = new Weapon("bullethell"){{
            reload = 900f;
            x = 0f;
            y = 0f;
            mirror = false;
            shots = 660;
            shotDelay = 1f;
            spacing = 10f;
            bullet = new BasicBulletType(){{
                damage = 20f;
                width = 16f;
                height = 16f;
                lifetime = 300f;
                speed = 3f;
                trailColor = Pal.remove;
                trailEffect = Fx.placeBlock;
            }};
        }};
    }
}