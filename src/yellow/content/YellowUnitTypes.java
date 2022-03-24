package yellow.content;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.util.*;
import arc.flabel.*;
import arc.struct.*;
import arc.struct.ObjectMap.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
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
import yellow.entities.units.*;
import yellow.entities.units.entity.*;

import static mindustry.Vars.*;

public class YellowUnitTypes implements ContentList{
    //Steal from Progressed Materials which stole Endless Rusting which stole from Progressed Materials in the past which stole from BetaMindy
    private static final Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
        prov(GhostUnitEntity.class, GhostUnitEntity::new),
    };

    private static final ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

    /**
     * Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
     * @author GlennFolker
     */
    private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov){
        Entry<Class<T>, Prov<T>> entry = new Entry<>();
        entry.key = type;
        entry.value = prov;
        return entry;
    }

    /**
     * Setups all entity IDs and maps them into {@link EntityMapping}.
     * @author GlennFolker
     */

    private static void setupID(){
        for(
            int i = 0,
            j = 0,
            len = EntityMapping.idMap.length;

            i < len;

            i++
        ){
            if(EntityMapping.idMap[i] == null){
                idMap.put(types[j].key, i);
                EntityMapping.idMap[i] = types[j].value;

                if(++j >= types.length) break;
            }
        }
    }

    /**
     * Retrieves the class ID for a certain entity type.
     * @author GlennFolker
     */
    public static <T extends Entityc> int classID(Class<T> type){
        return idMap.get(type, -1);
    }
    
    
    public static UnitType
    
    //yellow units
    yellow, yellowMech,
    
    //ghost units
    ghostFlare;
    
    //adios
    @Deprecated
    public static UnitType yellowAir, yellowGround;
    
    @Override
    public void load(){
        
        yellow = new UnitType("yellow"){{
            flying = true;
            hideDetails = false;
            health = 23000f;
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
            
            abilities.add(new RespawnAbility());
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
        
        yellowMech = new UnitType("yellow-mech"){{
            health = Float.MAX_VALUE;
            armor = Float.MAX_VALUE;
            speed = 1.5f;
            range = 400f;
            maxRange = 400f;
            
            constructor = MechUnit::create;
            
            weapons.add(YellowWeapons.antiMothSpray, YellowWeapons.decimation);
        }};
        
        EntityMapping.nameMap.put("ghost-flare", GhostUnitEntity::new);
        ghostFlare = new GhostUnitType("ghost-flare"){{
            flying = true;
            health = 37.5f;
            armor = 5f;
            speed = 3f;
            accel = 0.08f;
            drag = 0.01f;
            lifetime = 960f;
            
            defaultController = FlyingAI::new;
            region = Core.atlas.find("flare");
        }};
        
        yellowAir = yellow;
        yellowGround = yellowMech;
        //endregion
    };
}
