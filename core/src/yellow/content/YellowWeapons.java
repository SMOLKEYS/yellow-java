package yellow.content;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import yellow.entities.bullet.*;
import yellow.entities.bullet.AreaEffectPulse.*;
import yellow.math.*;
import yellow.type.weapons.*;
import yellow.util.*;

public class YellowWeapons{

    private static final Deathcatcher<Healthc> deaths = new Deathcatcher<>();
    private static final Timekeeper timer = Timekeeper.ofSeconds(2);

    // insane category
    public static ToggleWeapon
            laserBarrage, homingFlares, antiMothSpray, decimation, disruptor, ghostCall, ghostRain,
            traversal, octa, energySpheres, spearCall;

    // less insane category
    public static ToggleWeapon
            blasters;

    // "what the fuck" category
    public static ToggleWeapon
            gethsemane, contingency;

    // bullet hell category
    public static ToggleWeapon
            card1;

    public static void load(){
        laserBarrage = new ToggleWeapon("laser-barrage"){{
            x = 8*14;
            y = 0;
            reload = 60*5f;
            rotate = true;
            willMirror = true;
            inaccuracy = 10f;
            velocityRnd = 0.9f;
            visibility = WeaponVisibility.sandboxOnly;

            shootSound = Sounds.shootMeltdown;

            shoot.shots = 20;
            shoot.shotDelay = 2f;

            bullet = new ContinuousExtendingLaserBulletType(85){{
                speed = 40f;
                drag = 0.04f;
                length = 200f;
                lifetime = 60*3.5f;
                anchor = false;
                shrink = true;
                shrinkDelay = 60*2.5f;
                shrinkTime = 60f;
                shrinkInterp = Interp.pow2In;
                hitEffect = Fx.hitMeltdown;
                hitColor = Pal.meltdownHit;
                status = StatusEffects.melting;
                drawSize = 420f;
                pierceArmor = true;

                incendChance = 0.4f;
                incendSpread = 5f;
                incendAmount = 1;

                fragBullets = 5;
                fragRandomSpread = 0f;
                fragSpread = 72f;
                fragOnHit = false;

                fragBullet = new LaserBulletType(){{
                    damage = 150f;
                    length = 150f;

                    colors = new Color[]{Pal.accent, Pal.meltdownHit};
                }};
            }

                @Override
                public void hitEntity(Bullet b, Hitboxc entity, float health){
                    super.hitEntity(b, entity, health);

                    if(entity instanceof Healthc h && h.dead()) YellowGameStyles.melted.spawn(null);
                }
            };
        }};

        homingFlares = new ToggleWeapon("homing-flares"){{
            x = y = 0f;
            reload = 90f;
            rotate = true;
            predictTarget = false;
            ignoreRotation = true;
            visibility = WeaponVisibility.sandboxOnly;

            shootSound = Sounds.shootMissile;

            shoot = new ShootSpread(15, 10);

            bullet = new BasicEqualityBulletType(){{
                speed = 3f;
                damage = 80f;
                lifetime = 280f;
                width = height = 16;
                shrinkX = shrinkY = 0;

                homingDelay = 60;
                homingRange = 8*20f;
                homingPower = 0.09f;

                trailEffect = Fx.trailFade;
                trailLength = 20;

                splashDamage = 100f;
                splashDamageRadius = 8*3;

                hitSound = Sounds.explosion;
                hitEffect = despawnEffect = Fx.blastExplosion;

                sprite = "yellow-java-old-flare";
            }};
        }};

        antiMothSpray = new ToggleWeapon("anti-moth-spray"){{
            reload = 2f;
            x = 3f;
            willMirror = true;
            shoot.shots = 25;
            inaccuracy = 15f;
            visibility = WeaponVisibility.sandboxOnly;

            shootSound = Sounds.none;

            bullet = new BasicEqualityBulletType(){{
                damage = 20f;
                lifetime = 60f;
                velocityRnd = 0.6f;
                speed = 4f;
                width = 8f;
                height = 8f;
                knockback = 5f;
            }

                @Override
                public void hitEntity(Bullet b, Hitboxc entity, float health){
                    super.hitEntity(b, entity, health);

                    if(died(entity)){
                        Healthc h = (Healthc) entity;
                        if(h.maxHealth() >= 2500f){
                            YellowGameStyles.bigBugSpray.spawn(null);
                        }else{
                            YellowGameStyles.bugSpray.spawn(null);
                        }
                    }
                }
            };
        }};

        decimation = new ToggleWeapon("decimation"){{
            reload = 300f;
            y = 40f;
            minWarmup = 0.99f;
            shootWarmupSpeed = 0.05f;
            shootSound = Sounds.shootFlamePlasma;
            visibility = WeaponVisibility.sandboxOnly;

            shoot = new ShootSpread(8, 5){{
                firstShotDelay =
                shotDelay = 10f;
            }};

            parts.addAll(new HaloPart(){{
                shapes = 6;
                sides = 4;
                tri = true;
                triLength = 1f;
                triLengthTo = 13f;
                radius = 0f;
                radiusTo = 10f;
                haloRadius = 10f;
                haloRadiusTo = 35f;
                haloRotateSpeed = 0.5f;
                shapeMoveRot = 360f;
                color = Color.white;
                colorTo = Color.yellow;
                layer = Layer.bullet;

                progress = p -> Interp.pow2In.apply(p.warmup);
            }}, new ShapePart(){{
                hollow = true;
                sides = 6;
                radius = 0f;
                radiusTo = 22.5f;
                stroke = 0f;
                strokeTo = 4f;
                rotateSpeed = -2f;
                moveRot = 360f;
                color = Color.white;
                colorTo = Color.yellow;
                layer = Layer.bullet;

                //hmm
                InterpStack stack = new InterpStack(Interp.smooth, Interp.pow10In, Interp.pow10In);

                progress = p -> stack.apply(p.warmup);
            }});

            bullet = new BasicEqualityBulletType(){{
                damage = 2400f;
                splashDamage = 1540f;
                splashDamageRadius = 192f;
                lifetime = 420f;
                speed = 3f;
                width = 16f;
                height = 16f;
                hitEffect = YellowFx.decimatorPortalExplosion;
                despawnEffect = YellowFx.decimatorPortalExplosion;
                pierceArmor = true;
                keepVelocity = false;

                trailEffect = Fx.coreBurn;
                trailChance = 0.5f;

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
            }};
        }};

        disruptor = new ToggleWeapon("disruptor"){{
            reload = 60*20f;
            x = y = 0f;

            shootSound = Sounds.drillImpact;

            bullet = new AreaEffectPulse(8*40f,
                    new AppliedAreaEffect(StatusEffects.unmoving, 60*5f, 60*7f),
                    new AppliedAreaEffect(StatusEffects.disarmed, 60*2f, 60*5f)
            );
        }};

        ghostCall = new ToggleWeapon("ghost-call"){{
            reload = 240f;
            x = 24f;
            y = 0f;
            willMirror = true;
            predictTarget = false;
            ignoreRotation = true;
            visibility = WeaponVisibility.sandboxOnly;

            shoot.shots = 35;
        }};

        ghostRain = new ToggleWeapon("ghost-rain"){{
            reload = 30f;
            x = 80f;
            y = 0f;
            willMirror = true;
            ignoreRotation = true;
            visibility = WeaponVisibility.sandboxOnly;

            inaccuracy = 360f;

            shoot.shots = 20;
            shoot.shotDelay = 5;

            bullet = new ThrowBulletType(){{
                speed = 10f;
                drag = 0.1f;
                lifetime = 240f;
                damage = 40f;
                width = 12f;
                height = 12f;
                pierceArmor = true;
                hitEffect = YellowFx.ghostDespawnMulti;
                despawnEffect = YellowFx.ghostDespawnMulti;
                backRegion = Core.atlas.find("flare");
                frontRegion = Core.atlas.find("flare");
                sprite = "flare";
                shrinkX = 0f;
                shrinkY = 0f;
            }};
        }};

        traversal = new ToggleWeapon("traversal"){{
            enabledDefault = false;
            rotate = false;
            baseRotation = 180f;
            shootCone = 360f;
            x = y = 0f;
            alwaysContinuous = true;
            ignoreRotation = true;
            predictTarget = false;
            visibility = WeaponVisibility.sandboxOnly;

            shootSound = Sounds.shoot;

            bullet = new ContinuousEqualityFlameBulletType(){{
                recoil = 0.5f;
                damage = 60f;
                knockback = 50f;

                flareColor = Color.white;
                colors = new Color[]{Color.white, Color.yellow, Color.orange, Color.black};
            }

                @Override
                public void hitEntity(Bullet b, Hitboxc entity, float health){
                    super.hitEntity(b, entity, health);

                    if(entity instanceof Healthc h && h.dead()) YellowGameStyles.nyanCat.spawn(null);
                }
            };
        }};

        octa = new ToggleWeapon("octa"){{
            reload = 60*14f;
            x = y = 0f;
            visibility = WeaponVisibility.sandboxOnly;

            shoot = new ShootSpread(8, 45f);

            shootSound = Sounds.shootMeltdown;

            bullet = new RotatingContinuousLaserBulletType(120f){{
                length = 530f;
                grow = true;
                growTime = 60f;
                growDelay = 15f;
                interp = Interp.pow3Out;
                shrink = true;
                shrinkTime = 60*4f;
                shrinkDelay = 60*5.5f;
                shrinkDelayRandRange = 60f;
                lifetime = 60*10f;
                hitEffect = Fx.hitMeltdown;
                hitColor = Pal.meltdownHit;
                status = StatusEffects.melting;
                drawSize = 420f;
                pierceArmor = true;
                rotateSpeed = 0.4f;

                incendChance = 0.4f;
                incendSpread = 5f;
                incendAmount = 1;
            }};
        }};

        energySpheres = new ToggleWeapon("energy-spheres"){{
            reload = 60*2f;
            x = 8*20f;
            y = 0f;
            willMirror = true;
            visibility = WeaponVisibility.sandboxOnly;

            shoot = new ShootSpread(5, 5f);

            shootSound = Sounds.shootLaser;

            bullet = new EqualityBulletType(5f, 240f){{
                lifetime = 60*5f;
                drag = -0.007f;
                hitSize = 12f;
                hitEffect = YellowFx.energySphereExplosion;
                status = StatusEffects.shocked;
                pierceArmor = true;

                lightning = 5;
                lightningLength = 5;
                lightningDamage = 120f;
                lightningColor = Pal.lancerLaser;
                lightningType = new LightningBulletType();

                trailEffect = Fx.trailFade;
                trailColor = Pal.lancerLaser;
                trailLength = 20;
                trailWidth = 5f;
                lightOpacity = 1f;
                lightColor = Pal.lancerLaser;
                homingRange = 8*50f;
                homingPower = 0.01f;

                splashDamage = 200f;
                splashDamageRadius = 8*8f;

                parts.add(new ShapePart(){{
                    circle = true;
                    radius = 15f;
                    color = Pal.lancerLaser;
                }});
            }

                @Override
                public void hitEntity(Bullet b, Hitboxc entity, float health){
                    super.hitEntity(b, entity, health);

                    if(died(entity)){
                        YellowGameStyles.shocking.spawn(null);
                    }
                }
            };
        }};

        spearCall = new ToggleWeapon("spear-call"){{
            reload = 3.5f;
            x = 0f;
            y = 8*10f;
            xRand = 8*120f;
            willMirror = true;
            ignoreRotation = true;
            predictTarget = false;
            visibility = WeaponVisibility.sandboxOnly;

            properties = new Mirror.ReflectProperty[]{
                    Mirror.ReflectProperty.flipY
            };

            shootSound = Sounds.shootNavanax;

            bullet = new SpinSpearBulletType(){{
                damage = 80f;
                speed = 0.5f;
                drag = -0.03f;
                lifetime = 60*4f;
                launchTime = 60f;
                spinInterp = Interp.pow3Out;
                height = 75f;
                pierce = true;
                pierceCap = 3;
                pierceArmor = true;
                keepVelocity = false;

                sprite = "flare";
            }};
        }};

        blasters = new ToggleWeapon("blasters"){{
            reload = 10f;
            x = 4f;
            y = 2.3f;
            inaccuracy = 4f;
            willMirror = true;
            predictTarget = true;
            visibility = WeaponVisibility.sandboxOnly;

            shootSound = Sounds.shoot;

            bullet = new BasicEqualityBulletType(){{
                damage = 48f;
                speed = 10f;
                lifetime = 60f;

                frontColor = Pal.turretHeat;
                backColor = Pal.lancerLaser;

                trailEffect = Fx.trailFade;
                trailColor = Pal.lancerLaser;
                trailLength = 7;
            }};
        }};

        //the fuck is this?
        gethsemane = new ToggleWeapon("gethsemane"){{
            reload = 60*60f;
            x = y = 0f;
            shootY = -8*1000f;
            shootCone = 5f;
            visibility = WeaponVisibility.sandboxOnly;

            shootSound = Sounds.none;

            bullet = new BasicEqualityBulletType(){{
                speed = 40f;
                damage = splashDamage = 9500000f;
                splashDamageRadius = 8*80f;
                width = 8*45f;
                height = 8*85f;
                lifetime = 60*5f;
                keepVelocity = false;
                scaleLife = true;

                despawnSound = YellowSounds.gethsemaneExplosion;

                despawnEffect = new ExplosionEffect(){{
                    lifetime = 60*6;
                    smokes = 100;
                    sparks = 140;
                    sparkRad = 8*150;
                    sparkLen = 8*23;
                    sparkStroke = 8*6;
                    smokeRad = 8*140;
                    smokeSize = 8*23;
                    waveLife = 90;
                    waveRad = 8*170;
                    waveStroke = 8*25;
                }};

                trailEffect = Fx.trailFade;
                trailLength = 180;
                trailWidth = 8*13f;

                fragBullets = 10;
                fragBullet = new BasicEqualityBulletType(){{
                    speed = 7f;
                    damage = splashDamage = 450000f;
                    splashDamageRadius = 8*40f;
                    width = 8*25f;
                    height = 8*45f;
                    lifetime = 60*3f;
                    keepVelocity = false;

                    trailEffect = Fx.trailFade;
                    trailLength = 60;
                    trailWidth = 8*4f;

                    despawnSound = YellowSounds.gethsemaneExplosion;

                    despawnEffect = new ExplosionEffect(){{
                        lifetime = 60*4;
                        smokes = 50;
                        sparks = 80;
                        sparkRad = 8*90;
                        sparkLen = 8*15;
                        sparkStroke = 8*4;
                        smokeRad = 8*100;
                        smokeSize = 8*17;
                        waveLife = 60;
                        waveRad = 8*120;
                        waveStroke = 8*20;
                    }};
                }};
            }};
        }};

        contingency = new ToggleWeapon("contingency"){{
            reload = 60*5f;
            x = y = 0f;
            visibility = WeaponVisibility.sandboxOnly;

            shootSound = Sounds.shootLaser;

            bullet = new LaserBulletType(1000f){{
                length = 3000f;
                width = 30f;
                pierce = false;
                fragOnHit = false;

                fragBullets = 5;
                fragBullet = new LaserBulletType(800f){{
                    length = 3000f;
                    width = 30f;
                    pierce = false;
                    fragOnHit = false;

                    fragBullets = 5;
                    fragBullet = new LaserBulletType(600f){{
                        length = 3000f;
                        width = 30f;
                        pierce = false;
                        fragOnHit = false;

                        fragBullets = 5;
                        fragBullet = new LaserBulletType(400f){{
                            length = 3000f;
                            width = 30f;
                            pierce = false;
                            fragOnHit = false;

                            fragBullets = 5;
                        }};
                    }};
                }};
            }};
        }};

        card1 = new ToggleWeapon("card1"){{
            x = y = 0f;
            reload = 60*15;
            predictTarget = false;
            ignoreRotation = true;
            visibility = WeaponVisibility.sandboxOnly;

            shootSound = Sounds.none;

            shoot = new ShootSpread(){{
                shotDelay = 0.2f;
                spread = 25;
                shots = 250*8;
            }};

            bullet = new BasicEqualityBulletType(){{
                damage = 95;
                width = 12;
                height = 12;
                lifetime = 60*6;
                speed = 2.6f;
                sprite = "yellow-java-old-flare";
                despawnEffect = YellowFx.ghostDespawnMulti;
                trailEffect = Fx.trailFade;
                trailLength = 4;
                shrinkX = shrinkY = 0;
                weaveMag = 1.205f;
                pierce = true;
                pierceBuilding = true;
                buildingDamageMultiplier = 2.55f;
                pierceCap = 35;
                keepVelocity = false;
            }};
        }};
    }

    public static void afterLoad(){
        BulletType bul = new BulletType();
        bul.spawnUnit = YellowUnitTypes.ghostFlare;
        bul.load();
        ghostCall.bullet = bul;
        ghostCall.mirrored.bullet = bul;

        Events.run(Trigger.update, () -> {
            if(timer.poll()) deaths.clear();
        });
    }

    private static boolean died(Entityc entity){
        if(entity instanceof Healthc h && h.dead()){
            // track the death, because hitEntity can call multiple times
            return deaths.add(h);
        }
        return false;
    }
}
