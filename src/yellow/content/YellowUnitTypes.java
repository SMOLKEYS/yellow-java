package yellow.content;

import arc.*;
import mindustry.ai.types.*;
import mindustry.type.*;
import yellow.ai.*;
import yellow.entities.units.*;
import yellow.weapons.*;

public class YellowUnitTypes{

    public static UnitType
    
    //yellow units
    yellow,
    
    //ghost units
    ghostFlare;

    public static void load(){
        
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
            itemCapacity = 8500;
            buildSpeed = 95000f;
            deathExplosionEffect = YellowFx.yellowDeathEffect;
            alwaysShootWhenMoving = true;
            allowedInPayloads = false;
            createScorch = false;
            createWreck = false;
            
            aiController = FlyingAI::new;
            region = Core.atlas.find("yellow");
            
            weapons.addAll(YellowWeapons.meltdownBurstAttack, YellowWeapons.bullethell, YellowWeapons.airstrikeFlareLauncher, YellowWeapons.antiMothSpray, YellowWeapons.decimation, YellowWeapons.disruptor, YellowWeapons.ghostCall, YellowWeapons.ghostRain, YellowWeapons.speedEngine, YellowWeapons.dualSpeedEngine);
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
