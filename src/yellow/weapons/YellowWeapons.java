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
import yellow.util.*;
import yellow.content.*;
import yellow.entities.units.*;
import yellow.entities.bullet.*;

public class YellowWeapons{
    public static Weapon
    
    meltdownBurstAttack, bullethell, antiMothSpray, decimation, airstrikeFlareLauncher;
    
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
            
        }};
        
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
        }};
        
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
    }
}
