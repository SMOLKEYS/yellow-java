package yellow.weapons;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import yellow.content.*;
import yellow.entities.bullet.*;
import yellow.type.*;

public class YellowWeapons{
    public static Weapon
    
    meltdownBurstAttack, bullethell, antiMothSpray, decimation, airstrikeFlareLauncher, disruptor, ghostCall, ghostRain, speedEngine, dualSpeedEngine, isolator;
    
    public static void load(){
        
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
                hitEffect = YellowFx.ghostDespawnMulti;
                despawnEffect = YellowFx.ghostDespawnMulti;
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
                        color = colorTo = Color.yellow;
                    }},
                    new ShapePart(){{
                        sides = 3;
                        radius = 15f;
                        hollow = true;
                        layer = Layer.bullet;
                        rotateSpeed = 3f;
                        color = colorTo = Color.yellow;
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

            bullet = new PainfulPierceBulletType(15f, 30f, 10f){{
               lifetime = 600f;
               drag = 0.003f;
               weaveMag = 3f;
               weaveScale = 300f;
               hitSize = 12f;
               trailEffect = Fx.trailFade;
               trailLength = 3;
               
               parts.add(new FlarePart(){{
                   followRotation = true;
                   radius = 50f;
                   sides = 8;
               }});
            }};
        }};

        ghostCall = new DisableableWeapon("ghost-call", "Ghost Call"){{
            reload = 240f;
            x = 24f;
            y = 0f;
            mirror = false;
            minWarmup = 0.99f;
            
            shoot.shots = 35;
        }};
        
        ghostRain = new DisableableWeapon("ghost-rain", "Ghost Rain"){{
            reload = 30f;
            x = 80f;
            y = 0f;
            mirror = false;
            minWarmup = 0.99f;
            inaccuracy = 360f;
            
            shoot.shots = 20;
            shoot.shotDelay = 5;
            
            bullet = new YeetBulletType(){{
                speed = 10f;
                drag = 0.1f;
                lifetime = 240f;
                damage = 40f;
                width = 12f;
                height = 12f;
                homingRange = 80f;
                homingPower = 0.1f;
                hitEffect = YellowFx.ghostDespawnMulti;
                despawnEffect = YellowFx.ghostDespawnMulti;
                backRegion = Core.atlas.find("flare");
                frontRegion = Core.atlas.find("flare");
                sprite = "flare";
                shrinkX = 0f;
                shrinkY = 0f;
            }};
        }};
        
        speedEngine = new DistanceBasedWeapon("speed-engine", "Speed Engine"){{
            enabledDefault = false;
            rotate = false;
            baseRotation = 180f;
            shootCone = 360f;
            mirror = false;
            x = y = 0f;
            
            shootSound = Sounds.pulse;
            
            bullet = new ContinuousFlameBulletType(){{
                recoil = -0.32f;
                damage = 60f;
                knockback = 50f;
                
                flareColor = Color.yellow;
                colors = new Color[]{Color.yellow, Color.orange};
            }};
        }};

        dualSpeedEngine = new DistanceBasedWeapon("dual-speed-engine", "Dual Speed Engine"){{
           enabledDefault = false;
           rotate = false;
           holdTime = 120f;
           baseRotation = 215f;
           shootCone = 360f;
           mirror = false;
           x = y = 0f;

           shootSound = Sounds.none;

            bullet = new ContinuousFlameBulletType(){{
                recoil = -0.32f;
                damage = 60f;
                knockback = 50f;
                length = 45f;

                flareColor = Color.yellow;
                colors = new Color[]{Color.yellow, Color.orange};
            }};
        }};
    }
    
    public static void afterLoad(){
        BulletType bul = new BulletType();
        bul.spawnUnit = YellowUnitTypes.ghostFlare;
        bul.load();
        ghostCall.bullet = bul;
    }
}
