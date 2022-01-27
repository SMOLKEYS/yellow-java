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
import mindustry.gen.*;
import mindustry.type.*;
import yellow.content.*;

public class YellowWeapons{
    public static Weapon
    
    meltdownBurstAttack, bullethell, antiMothSpray, decimation;
    
    public void init(){
        meltdownBurstAttack = new Weapon("Meltdown Burst"){{
            reload = 60f;
            x = 56f;
            mirror = true;
            shots = 15;
            shotDelay = 5f;
            shootSound = Sounds.explosionbig;
            spacing = 5f;
            continuous = true;
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
                
                if(w.reload > this.reload) return;
                
                float rotation = u.rotation - 90;
                
                Draw.color(Color.yellow);
                Draw.z(Layer.effect);
                Lines.square(u.x + Angles.trnsx(rotation, w.weapon.x, w.weapon.y), u.y + Angles.trnsy(rotation, w.weapon.x, w.weapon.y), 30, Time.time);
                Lines.square(u.x + Angles.trnsx(rotation, w.weapon.x, w.weapon.y), u.y + Angles.trnsy(rotation, w.weapon.x, w.weapon.y), 30,-Time.time);
            }
        };
        
        bullethell = new Weapon("Bullethell"){{
            reload = 900f;
            ejectEffect = YellowFx.bullethellShootEffect;
            x = 0f;
            y = 0f;
            mirror = false;
            shots = 690;
            shotDelay = 1f;
            spacing = 60f;
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
                
                if(w.reload > this.reload) return;
                
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
        
        antiMothSpray = new Weapon("Anti Moth Spray"){{
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
        
        decimation = new Weapon("Decimation"){{
            reload = 300f;
            x = 48f;
            mirror = true;
            shots = 1;
            inaccuracy = 0f;
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
        
        //endregion
    }
}