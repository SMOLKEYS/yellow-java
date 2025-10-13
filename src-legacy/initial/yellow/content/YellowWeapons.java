package yellow.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class YellowWeapons{
    public static DisableableWeapon
    meltdownBurstAttack, bullethell, antiMothSpray, decimation, airstrikeFlareLauncher, disruptor, ghostCall, ghostRain, speedEngine, dualSpeedEngine, igneous;

    public static void load(){
        
        meltdownBurstAttack = new DisableableWeapon("meltdown-burst"){{
            reload = 60f;
            x = 56f;
            mirror = false;
            shootSound = Sounds.explosionbig;
            
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

        //not an actual bullethell weapon and cant work as one, somehow
        bullethell = new DisableableWeapon("bullethell"){{
            reload = 900f;
            ejectEffect = YellowFx.bullethellShootEffect;
            x = 0f;
            y = 0f;
            mirror = false;
            
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
        
        antiMothSpray = new DisableableWeapon("anti-moth-spray"){{
            reload = 2f;
            x = 3f;
            mirror = false;
            shoot.shots = 25;
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
        
        decimation = new DisableableWeapon("decimation"){{
            reload = 300f;
            x = 48f;
            mirror = false;
            shoot.shots = 8;
            inaccuracy = 35f;

            shootSound = Sounds.artillery;

            bullet = new BasicBulletType(){{
                damage = 1100f;
                splashDamage = 990f;
                splashDamageRadius = 192f;
                lifetime = 420f;
                speed = 3f;
                width = 16f;
                height = 16f;
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
        
        airstrikeFlareLauncher = new DisableableWeapon("airstrike-flare-launcher"){{
            reload = 300f;
            x = 0f;
            y = 0f;
            mirror = false;

            bullet = YellowBullets.standardMissileCaller;
        }};

        disruptor = new DisableableWeapon("disruptor"){{
            reload = 600f;
            x = 0f;
            y = 0f;
            mirror = false;
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

        ghostCall = new DisableableWeapon("ghost-call"){{
            reload = 240f;
            x = 24f;
            y = 0f;
            mirror = false;
            
            shoot.shots = 35;
        }};
        
        ghostRain = new DisableableWeapon("ghost-rain"){{
            reload = 30f;
            x = 80f;
            y = 0f;
            mirror = false;

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
                hitEffect = YellowFx.ghostDespawnMulti;
                despawnEffect = YellowFx.ghostDespawnMulti;
                backRegion = Core.atlas.find("flare");
                frontRegion = Core.atlas.find("flare");
                sprite = "flare";
                shrinkX = 0f;
                shrinkY = 0f;
            }};
        }};
        
        speedEngine = new DistanceBasedWeapon("speed-engine"){{
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

        dualSpeedEngine = new DistanceBasedWeapon("dual-speed-engine"){{
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

        igneous = new DisableableWeapon("igneous"){{
            reload = 60f;
            x = 40f;
            y = 0f;

            shootSound = Sounds.bolt;

            shoot = new ShootSpread(){{
                shots = 7;
                spread = 4f;
            }};

            bullet = new BasicBulletType(){{
                damage = 15f;
                speed = 5f;
                lifetime = 180f;
                status = StatusEffects.burning;
                trailChance = 0.8f;
                trailEffect = Fx.fire;
                pierce = true;
                homingRange = 80f;
                homingPower = 0.01f;
                width = height = 16f;
                hitEffect = Fx.fireHit;
            }
                @Override
                public void hitEntity(Bullet b, Hitboxc entity, float health) {
                    super.hitEntity(b, entity, health);
                    if(entity instanceof Statusc){
                        Statusc un = (Statusc) entity;

                        if(un.hasEffect(StatusEffects.burning)) un.apply(StatusEffects.melting, 30f);
                    }
                }
            };
        }};
    }
    
    public static void afterLoad(){
        BulletType bul = new BulletType();
        bul.spawnUnit = YellowUnitTypes.ghostFlare;
        bul.load();
        ghostCall.bullet = bul;
    }
}
