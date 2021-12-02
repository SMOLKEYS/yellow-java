package yellow.content;

import arc.*;
import arc.util.*;
import arc.util.Time.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.graphics.Color.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.game.Team.*;
import mindustry.graphics.*;
import mindustry.ai.types.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import yellow.weapons.*;
import yellow.content.YellowFx.*;

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
            health = Float.MAX_VALUE;
            armor = Float.MAX_VALUE;
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
            deathExplosionEffect = YellowFx.yellowExplosionOutIn;
            
            constructor = UnitEntity::create;
            defaultController = FlyingAI::new;
            region = Core.atlas.find("yellow");
            
            weapons.add(YellowWeapons.meltdownBurstAttack, YellowWeapons.bullethell);
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
                stats.add(Stat.health, l -> l.add(new FLabel("{shake}[yellow]very high")));
                stats.add(Stat.armor, l -> l.add(new FLabel("{wind}[red]many")));
                stats.add(Stat.size, l -> l.add(new FLabel("{jump}shmol")));
                stats.add(Stat.commandLimit, l -> l.add(new FLabel("{shake}[navy]no other survived.")));
                stats.add(Stat.speed, l -> l.add(new FLabel("{sick}flar")));
                stats.add(Stat.weapons, l -> l.add(new FLabel("{shake}[red]Meltdown Shotgun")));
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
                
                float rot = Time.time * 2f;
                
                Draw.z(Layer.effect);
                Draw.rect(Core.atlas.find("yellow-java-rotato"), u.x, u.y, 20, 20, 0, 0, rot);
                Draw.rect(Core.atlas.find("yellow-java-rotato"), u.x, u.y, 20, 20, 20, 20, rot);
                Draw.rect(Core.atlas.find("yellow-java-rotato"), u.x, u.y, 20, 20, 20, 20, -rot);
                Draw.rect(Core.atlas.find("yellow-java-rotato"), u.x, u.y, 20, 20, 0, 0, -rot);
            }
            
            @Override
            public void create(Team team){
                super.create(team);
                
                Vars.ui.hudfrag.showToast(Icon.power, "[yellow]Yellow (Air)[] has been spawned!");
            }
        };
        
        yellowGround = new UnitType("yellowGround"){{
            health = Float.MAX_VALUE;
            armor = Float.MAX_VALUE;
            speed = 1.5f;
            range = 400f;
            maxRange = 400f;
            
            constructor = MechUnit::create;
            region = Core.atlas.find("yellow");
            
            weapons.add(YellowWeapons.antiMothSpray, YellowWeapons.decimation);
        }};
        
        //endregion
    };
}