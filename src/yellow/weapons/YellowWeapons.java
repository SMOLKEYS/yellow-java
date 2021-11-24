package yellow.weapons;

import arc.*;
import arc.math.*;
import arc.math.Angles.*;
import arc.util.*;
import arc.util.Time.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.Draw.*;
import arc.graphics.Color.*;
import arc.flabel.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.entities.units.WeaponMount.*;
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
    
    meltdownBurstAttack, bullethellAttack, antiMothSpray;
    
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
        }
            @Override
            public void draw(Unit u, WeaponMount w){
                super.draw(u, w);
                
                float rotation = u.rotation - 90;
                
                Draw.z(Layer.effect);
                Draw.rect(Core.atlas.find("yellow-yellow-square"), u.x + Angles.trnsx(rotation, w.weapon.x, w.weapon.y), u.y + Angles.trnsy(rotation, w.weapon.x, w.weapon.y), 25, 25, Time.time);
                Draw.rect(Core.atlas.find("yellow-yellow-square"), u.x + Angles.trnsx(rotation, w.weapon.x, w.weapon.y), u.y + Angles.trnsy(rotation, w.weapon.x, w.weapon.y), 30, 30, Time.time);
            }
        };
        
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
                hitEffect = Fx.none;
                backRegion = Core.atlas.find("flare");
                frontRegion = Core.atlas.find("flare");
                sprite = "flare";
                backColor = Color.white;
                frontColor = Color.white;
                trailColor = Color.yellow;
                trailChance = 1f;
                trailEffect = Fx.trailFade;
                trailLength = 10;
                shrinkX = 0f;
                shrinkY = 0f;
                weaveMag = 1.205f;
                weaveScale = 4000f;
            }};
        }};
        
        antiMothSpray = new Weapon("anti-moth-spray"){{
            reload = 2f;
            x = 3f;
            mirror = true;
            shots = 25;
            inaccuracy = 15f;
            bullet = new BasicBulletType(){{
                damage = 20f;
                lifetime = 60f;
                speed = 4f;
                width = 8f;
                height = 8f;
                knockback = 5f;
            }};
        }};
    }
}