package yellow.content;

import arc.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.type.*;
import yellow.ai.*;
import yellow.entities.units.*;

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
            drag = 0.03f;
            range = 8*30f;
            maxRange = 8*30f;
            mineSpeed = 500f;
            mineTier = 500;
            itemCapacity = 8500;
            buildSpeed = 950f;
            deathExplosionEffect = YellowFx.yellowDeathEffect;
            alwaysShootWhenMoving = true;
            allowedInPayloads = false;
            createScorch = false;
            createWreck = false;

            commands = new UnitCommand[]{YellowUnitCommand.wander, YellowUnitCommand.fly, UnitCommand.assistCommand, UnitCommand.moveCommand};
            defaultCommand = UnitCommand.assistCommand;
            
            aiController = FlyingAI::new;
            region = Core.atlas.find("yellow");

            getAfterDeath()[0] = a -> {
                for(int i = 0; i < 360; i++) YellowBullets.wave.create(a, a.x, a.y, i);
            };

            spells.addAll(YellowSpells.fireCircle, YellowSpells.dash);
            weapons.addAll(YellowWeapons.meltdownBurstAttack, YellowWeapons.bullethell, YellowWeapons.airstrikeFlareLauncher, YellowWeapons.antiMothSpray, YellowWeapons.decimation, YellowWeapons.disruptor, YellowWeapons.ghostCall, YellowWeapons.ghostRain, YellowWeapons.speedEngine, YellowWeapons.dualSpeedEngine, YellowWeapons.igneous);
        }};
        
        ghostFlare = new GhostUnitType("ghost-flare"){{
            flying = true;
            health = 37.5f;
            armor = 5f;
            speed = 5.5f;
            accel = 0.08f;
            drag = 0.01f;
            lifetime = 960f;
            
            controller = u -> new YellowOrbiterAI();
            region = Core.atlas.find("flare");
        }};
        
        //endregion
    }

}
