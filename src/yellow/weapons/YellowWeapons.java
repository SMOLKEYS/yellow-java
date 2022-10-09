package yellow.weapons;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.part.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.Weapon;
import yellow.content.YellowBullets;
import yellow.content.YellowFx;
import yellow.type.DisableableWeapon;

public class YellowWeapons{
    public static Weapon
    
    meltdownBurstAttack, bullethell, antiMothSpray, decimation, airstrikeFlareLauncher, disruptor;
    
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
                spread = 25f;
            }};
            
            bullet = new BasicBulletType(){{
                damage = 300f;
                width = 12f;
                height = 12f;
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
            shoot.shots = 8;
            inaccuracy = 35f;
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
                
                parts.add(
                    new ShapePart(){{
                        sides = 6;
                        radius = 10f;
                        layer = Layer.bullet;
                        rotateSpeed = 4f;
                        color = Color.yellow;
                    }},
                    new ShapePart(){{
                        sides = 3;
                        radius = 15f;
                        layer = Layer.bullet;
                        rotateSpeed = 3f;
                        mirror = true;
                    }}
                );
            }
                @Override
                public void draw(Bullet b){
                    super.draw(b);
                    /*
                    Draw.z(Layer.effect);
                    Draw.color(Color.yellow);
                    Lines.square(b.x, b.y, 15, Time.time * 2);
                    Lines.square(b.x, b.y, 15, -Time.time * 2);
                    Fill.circle(b.x, b.y, Mathf.sin(Time.time * 0.1f) * 1 + 4);
                    */
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

        disruptor = new DisableableWeapon("disruptor", "Disruptor"){{
            reload = 600f;
            x = 0f;
            y = 0f;
            mirror = false;
            minWarmup = 0.99f;
            inaccuracy = 360f;

            shoot.shots = 350;

            bullet = new BasicBulletType(){{
               lifetime = 600f;
               speed = 15f;
               drag = 0.003f;
               weaveMag = 3f;
               weaveScale = 300f;
               damage = 65f;
               width = 8f;
               height = 8f;
               trailEffect = Fx.trailFade;
               trailLength = 3;
               
               parts.add(new FlarePart(){{
                   followRotation = true;
                   radius = 50f;
                   sides = 8;
               }});
            }};
        }};
        
        //endregion main
    }
}
