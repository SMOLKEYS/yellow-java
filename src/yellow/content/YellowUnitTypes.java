package yellow.content;

import arc.*;
import arc.util.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.ai.types.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import mindustry.game.*;
import yellow.weapons.*;
import yellow.entities.abilities.*;

import static mindustry.Vars.*;

public class YellowUnitTypes implements ContentList{
    public static UnitType
    
    //yellow units
    yellowAir, yellowGround;
    
    @Override
    public void load(){
        
        yellowAir = new UnitType("yellowAir"){{
            flying = true;
            hideDetails = false;
            health = 230000f;
            armor = 5f;
            speed = 3f;
            accel = 0.08f;
            drag = 0.01f;
            range = 240f;
            maxRange = 240f;
            mineSpeed = 5000f;
            mineTier = 5000;
            itemCapacity = 850000;
            commandLimit = 85000;
            buildSpeed = 95000f;
            deathExplosionEffect = YellowFx.yellowDeathEffect;
            
            constructor = UnitEntity::create;
            defaultController = FlyingAI::new;
            region = Core.atlas.find("yellow");
            
            weapons.add(YellowWeapons.meltdownBurstAttack, YellowWeapons.bullethell);
            
            abilities.add(new ImmortalAbility());
        }
        
            /** readability 0 */
            @Override
            public void setStats(){
                super.setStats();
                stats.remove(Stat.health);
                stats.remove(Stat.armor);
                stats.remove(Stat.size);
                stats.remove(Stat.commandLimit);
                stats.remove(Stat.speed);
                stats.remove(Stat.weapons);
                stats.remove(Stat.itemCapacity);
                stats.remove(Stat.mineSpeed);
                stats.remove(Stat.mineTier);
                stats.remove(Stat.flying);
                stats.remove(Stat.range);
                stats.remove(Stat.buildSpeed);
                /** FLabel spam might not be a good idea, performance-wise */
                stats.add(Stat.health, l -> l.add(new FLabel("{shake}[yellow]230000")));
                stats.add(Stat.armor, l -> l.add(new FLabel("{wind}5")));
                stats.add(Stat.size, l -> l.add(new FLabel("{jump}shmol")));
                stats.add(Stat.commandLimit, l -> l.add(new FLabel("{shake}[navy]no other survived.")));
                stats.add(Stat.speed, l -> l.add(new FLabel("{sick}flar")));
                stats.add(Stat.weapons, "Meltdown Burst + Bullethell MK1");
                stats.add(Stat.itemCapacity, l -> l.add(new FLabel("{shake}[cyan]EVEN MORE ZEROOOOOOOOOOOOOOOOOOOOS")));
                stats.add(Stat.mineSpeed, l -> l.add(new FLabel("{wave}ULTRA FAST")));
                stats.add(Stat.mineTier, l -> l.add(new FLabel("{rainbow}{sick}ONE AND THIRTY ZEROS")));
                stats.add(Stat.flying, l -> l.add(new FLabel("{rainbow}YES")));
                stats.add(Stat.range, l -> l.add(new FLabel("{shake}380729 + 125211 blocks")));
                stats.add(Stat.buildSpeed, l -> l.add(new FLabel("{rainbow}MANY ZEROS")));
            }
            
            @Override
            public void draw(Unit u){
                super.draw(u);
                
                /**
                 * @author MEEPOfFaith
                 * god help me
                 */
                float s = Mathf.absin(Time.time, 16, 1);
                float r1 = s * 25f;
                float r2 = s * 20f;
                
                Draw.z(Layer.effect);
                Draw.color(Color.yellow);
                Lines.circle(u.x, u.y, 20f + r1);
                Lines.square(u.x, u.y, 20f + r1, Time.time);
                Lines.square(u.x, u.y, 20f + r1, -Time.time);
                Tmp.v1.trns(Time.time, r2, r2);
                Fill.circle(u.x + Tmp.v1.x, u.y + Tmp.v1.y, 2f + s * 8f);
                Tmp.v1.trns(Time.time, -r2, -r2);
                Fill.circle(u.x + Tmp.v1.x, u.y + Tmp.v1.y, 2f + s * 8f);
                Tmp.c1.set(Color.white);
                Tmp.c1.a = 0;
                Fill.light(u.x, u.y, 5, 50f - r1, Color.yellow, Tmp.c1);
            }
            
            @Override
            public void update(Unit unit){
                super.update(unit);
                int realityCheck = Team.sharded.data().countType(unit.type);
            }
            
        };
        
        yellowGround = new UnitType("yellowGround"){{
            health = Float.MAX_VALUE;
            armor = Float.MAX_VALUE;
            speed = 1.5f;
            range = 400f;
            maxRange = 400f;
            
            constructor = MechUnit::create;
            
            weapons.add(YellowWeapons.antiMothSpray, YellowWeapons.decimation);
        }};
        
        //endregion
    };
}