package yellow.weapons;

import arc.*;
import arc.math.*;
import arc.math.Angles.*;
import arc.util.*;
import arc.util.Time.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.Draw.*;
import arc.graphics.g2d.Lines.*;
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
import yellow.content.*;

import static arc.Core.*;

public class YellowWeapons{
    public static Weapon
    
    meltdownBurstAttack, bullethell, antiMothSpray, decimation;
    
    public static void init(){
        meltdownBurstAttack = new Weapon("meltdown-burst"){{
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
                
                float rotation = u.rotation - 90;
                
                Draw.color(Color.yellow);
                Draw.z(Layer.effect);
                Lines.square(u.x + Angles.trnsx(rotation, w.weapon.x, w.weapon.y), u.y + Angles.trnsy(rotation, w.weapon.x, w.weapon.y), 30, Time.time);
                Lines.square(u.x + Angles.trnsx(rotation, w.weapon.x, w.weapon.y), u.y + Angles.trnsy(rotation, w.weapon.x, w.weapon.y), 30,-Time.time);
            }
        };
        
        bullethell = new Weapon("bullethell"){{
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
                hitEffect = YellowFx.ghostDespawn;
                despawnEffect = YellowFx.ghostDespawn;
                backRegion = Core.atlas.find("flare");
                frontRegion = Core.atlas.find("flare");
                sprite = "flare";
                backColor = Color.white;
                frontColor = Color.white;
                shrinkX = 0f;
                shrinkY = 0f;
                weaveMag = 1.205f;
                weaveScale = 4000f;
                pierce = true;
                pierceBuilding = true;
                pierceCap = 35;
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
        
        decimation = new Weapon("decimation"){{
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
                    Lines.spikes(b.x, b.y, 10, 5, 10, -Time.time);
                    Lines.spikes(b.x, b.y, 15, 5, 10, Time.time);
                    Lines.spikes(b.x, b.y, 20, 5, 10, -Time.time);
                    Fill.circle(b.x, b.y, Mathf.sin(Time.time * 0.1f) * 1 + 4);
                }
            };
        }};
        
        //endregion
    }
}