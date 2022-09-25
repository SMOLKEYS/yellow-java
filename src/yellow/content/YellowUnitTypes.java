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
import yellow.ctype.*;
import yellow.weapons.*;
import yellow.entities.abilities.*;
import yellow.entities.units.*;
import yellow.entities.units.entity.*;

import static mindustry.Vars.*;

public class YellowUnitTypes implements FallbackContentList{
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
    
    @Override
    public void load(){
        
        yellow = new YellowUnitType("yellow"){{
            flying = true;
            hideDetails = false;
            health = 23000f;
            armor = 5f;
            speed = 3f;
            accel = 0.08f;
            drag = 0.01f;
            range = 1250f;
            maxRange = 1250f;
            mineSpeed = 5000f;
            mineTier = 5000;
            itemCapacity = 850000;
            buildSpeed = 95000f;
            deathExplosionEffect = YellowFx.yellowDeathEffect;
            
            aiController = FlyingAI::new;
            region = Core.atlas.find("yellow");
            
            weapons.addAll(YellowWeapons.meltdownBurstAttack, YellowWeapons.bullethell, YellowWeapons.airstrikeFlareLauncher, YellowWeapons.antiMothSpray, YellowWeapons.decimation);
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
            
            aiController = FlyingAI::new;
            region = Core.atlas.find("flare");
        }};
        
        //endregion
    };
}
