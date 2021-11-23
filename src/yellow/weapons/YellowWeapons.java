package yellow.weapons;

import arc.*;
import arc.graphics.*;
import arc.graphics.Color.*;
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

import static arc.Core.*;

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
                damage = 150f;
                width = 8f;
                length = 240f;
                lifetime = 60f;
                lightColor = Color.yellow;
            }};
        }};
        
        bullethellAttack = new Weapon("bullethell"){{
            reload = 900f;
            x = 0f;
            y = 0f;
            mirror = false;
            shots = 690;
            shotDelay = 1f;
            spacing = 5f;
            bullet = new BasicBulletType(){{
                damage = 300f;
                width = 16f;
                height = 16f;
                lifetime = 300f;
                speed = 6f;
                backRegion = Core.atlas.find("flare");
                frontRegion = Core.atlas.find("flare");
                backColor = Color.white;
                frontColor = Color.white;
                trailColor = Color.yellow;
                trailChance = 1f;
                trailEffect = Fx.trailFade;
                trailLength = 25;
                shrinkX = 0f;
                shrinkY = 0f;
                weaveMag = 1.205f;
                weaveScale = 4000f;
            }};
        }};
    }
}