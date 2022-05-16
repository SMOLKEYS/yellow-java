package yellow.weapons;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.units.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import yellow.type.*;
import yellow.content.*;
import yellow.entities.units.*;
import yellow.entities.bullet.*;

public class YellowWeapons{
    public static Weapon
    
    meltdownBurstAttack, bullethell, antiMothSpray, decimation, airstrikeFlareLauncher,
    
    //mirrored variants
    //why not just mirror = true? well, custom weapon typws with mirror break, and i cannot, for the love of me, find the issue
    meltdownBurstAttack2, antiMothSpray2, decimation2, airstrikeFlareLauncher2;
    
    public static void init(){
        
        //region main
        
        meltdownBurstAttack = new DisableableWeapon("meltdown-burst", "Meltdown Burst"){{
            reload = 60f;
            x = 56f;
            mirror = false;
            shootSound = Sounds.explosionbig;
            minWarmup = 0.99f;
            
            shoot = new ShootSpread(){{
                shots = 15;
                shotDelay = 5f;
                spread = 5f;
            }};
            
            
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
                
                if(((DisableableWeaponMount) w).disabled) return;
                
                float rotation = u.rotation - 90;
                
                Draw.color(Color.yellow);
                Draw.z(Layer.effect);
                Lines.square(u.x + Angles.trnsx(rotation, w.weapon.x, w.weapon.y), u.y + Angles.trnsy(rotation, w.weapon.x, w.weapon.y), 30, Time.time);
                Lines.square(u.x + Angles.trnsx(rotation, w.weapon.x, w.weapon.y), u.y + Angles.trnsy(rotation, w.weapon.x, w.weapon.y), 30,-Time.time);
            }
        };
        
        bullethell = new DisableableWeapon("bullethell", "Bullethell"){{
            reload = 900f;
            ejectEffect = YellowFx.bullethellShootEffect;
            x = 0f;
            y = 0f;
            mirror = false;
            minWarmup = 0.99f;
            
            shoot = new ShootSpread(){{
                shots = 690;
                shotDelay = 1f;
                spread = 60f;
            }};
            
            bullet = new BasicBulletType(){{
                damage = 300f;
                width = 16f;
                height = 16f;
                lifetime = 300f;
                speed = 6f;
                hitEffect = YellowFx.ghostDespawn;
                despawnEffect = YellowFx.ghostDespawn;
                backRegion = Core.atlas.find("flare");
                frontRegion = Core.atlas.find("flare");
                sprite = "flare";
                trailEffect = Fx.trailFade;
                trailLength = 3;
                shrinkX = 0f;
                shrinkY = 0f;
                weaveMag = 1.205f;
                weaveScale = 4000f;
                pierce = true;
                pierceBuilding = true;
                pierceCap = 35;
            }
                @Override
                public void draw(Bullet b){
                    super.draw(b);
                    Draw.z(Layer.flyingUnit);
                }
            };
        }
            @Override
            public void draw(Unit u, WeaponMount w){
                super.draw(u, w);
                
                if(((DisableableWeaponMount) w).disabled) return;
                
                Draw.color(Color.yellow);
                Draw.z(Layer.effect);
                Draw.alpha(reloadf(w));
                
                Lines.square(u.x, u.y, 40, Time.time * 6);
                Lines.square(u.x, u.y, 40, -Time.time * 6);
                Lines.square(u.x, u.y, 80, Time.time * 6);
                Lines.square(u.x, u.y, 80, -Time.time * 6);
                
                Lines.stroke(10);
                Lines.poly(u.x, u.y, 3, 130, Time.time * 6);
                Lines.poly(u.x, u.y, 3, 130, Time.time * 6 - 180);
            }
            
            public float reloadf(WeaponMount w){
              return w.reload/this.reload;
            }
            
        };
        
        antiMothSpray = new DisableableWeapon("anti-moth-spray", "Anti Moth Spray"){{
            reload = 2f;
            x = 3f;
            mirror = false;
            shoot.shots = 25;
            inaccuracy = 15f;
            minWarmup = 0.99f;
            bullet = new BasicBulletType(){{
                damage = 20f;
                lifetime = 60f;
                speed = 4f;
                width = 8f;
                height = 8f;
                knockback = 5f;
            }};
        }};
        
        decimation = new DisableableWeapon("decimation", "Decimation"){{
            reload = 300f;
            x = 48f;
            mirror = false;
            shoot.shots = 1;
            inaccuracy = 0f;
            minWarmup = 0.99f;
            bullet = new BasicBulletType(){{
                damage = 8500f;
                splashDamage = 7000f;
                splashDamageRadius = 192f;
                lifetime = 420f;
                speed = 2f;
                width = 8f;
                height = 8f;
                hitEffect = YellowFx.yellowExplosionOut;
                despawnEffect = YellowFx.yellowExplosionOut;
            }
                @Override
                public void draw(Bullet b){
                    super.draw(b);
                    
                    Draw.z(Layer.effect);
                    Draw.color(Color.yellow);
                    Lines.square(b.x, b.y, 15, Time.time * 2);
                    Lines.square(b.x, b.y, 15, -Time.time * 2);
                    Fill.circle(b.x, b.y, Mathf.sin(Time.time * 0.1f) * 1 + 4);
                }
            };
        }};
        
        airstrikeFlareLauncher = new DisableableWeapon("airstrike-flare-launcher", "Airstrike Flare Launcher"){{
            reload = 300f;
            x = 0f;
            y = 0f;
            mirror = false;
            minWarmup = 0.99f;
            bullet = YellowBullets.standardMissileCaller;
        }};
        
        //endregion main
        
        //region mirrored
        
        meltdownBurstAttack2 = meltdownBurstAttack.copy();
        antiMothSpray2 = antiMothSpray.copy();
        decimation2 = decimation.copy();
        airstrikeFlareLauncher2 = airstrikeFlareLauncher.copy();
        
        meltdownBurstAttack2.x = meltdownBurstAttack.x - (meltdownBurstAttack.x * 2);
        meltdownBurstAttack2.reload = meltdownBurstAttack.reload * 2;
        meltdownBurstAttack2.name = "meltdown-burst-m";
        ((NameableWeapon) meltdownBurstAttack2).displayName = "Meltdown Burst (Inv)";
        
        antiMothSpray2.x = antiMothSpray.x - (antiMothSpray.x * 2);
        antiMothSpray2.reload = antiMothSpray.reload * 2;
        antiMothSpray2.name = "anti-moth-spray-m";
        ((NameableWeapon) antiMothSpray2).displayName = "Anti Moth Spray (Inv)";
        
        decimation2.x = decimation.x - (decimation.x * 2);
        decimation2.reload = decimation.reload * 2;
        decimation2.name = "decimation-m";
        ((NameableWeapon) decimation2).displayName = "Decimation (Inv)";
        
        airstrikeFlareLauncher2.x = airstrikeFlareLauncher.x - (airstrikeFlareLauncher.x * 2);
        airstrikeFlareLauncher2.reload = airstrikeFlareLauncher.reload * 2;
        airstrikeFlareLauncher2.name = "airstrike-flare-launcher-m";
        ((NameableWeapon) airstrikeFlareLauncher2).displayName = "Airstrike Flare Launcher (Inv)";
        
        //endregion mirrored
    }
}
