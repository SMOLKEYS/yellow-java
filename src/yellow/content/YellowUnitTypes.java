package yellow.content;

import arc.Core;
import arc.flabel.FLabel;
import mindustry.ai.types.FlyingAI;
import mindustry.type.UnitType;
import yellow.ai.*;
import yellow.ctype.FallbackContentList;
import yellow.entities.units.GhostUnitType;
import yellow.entities.units.YellowUnitType;
import yellow.weapons.YellowWeapons;
import yellow.world.meta.YellowStats;

public class YellowUnitTypes implements FallbackContentList{

    public static UnitType
    
    //yellow units
    yellow,
    
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
            
            weapons.addAll(YellowWeapons.meltdownBurstAttack, YellowWeapons.bullethell, YellowWeapons.airstrikeFlareLauncher, YellowWeapons.antiMothSpray, YellowWeapons.decimation, YellowWeapons.disruptor, YellowWeapons.ghostCall);
        }};
        
        ghostFlare = new GhostUnitType("ghost-flare"){{
            flying = true;
            health = 37.5f;
            armor = 5f;
            speed = 5.5f;
            accel = 0.08f;
            drag = 0.01f;
            lifetime = 960f;
            
            controller = u -> new YellowFollowerAI();
            region = Core.atlas.find("flare");
        }};
        
        //endregion
    }

}
