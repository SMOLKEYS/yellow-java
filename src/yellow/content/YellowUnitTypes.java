package yellow.content;

import mindustry.graphics.*;
import mindustry.type.*;
import yellow.ai.*;
import yellow.entities.units.*;
import yellow.type.weapons.*;

public class YellowUnitTypes{

    public static UnitType yellow, ghostFlare;

    public static void load(){

        yellow = new YellowUnitType("yellow"){{
            health = 2000f;
            flying = true;
            hideDetails = false;
            armor = 17f;
            speed = 4f;
            accel = 0.08f;
            drag = 0.03f;
            range = 8*70f;
            maxRange = 8*90f;
            mineSpeed = 500f;
            mineTier = 500;
            itemCapacity = 850;
            buildSpeed = 950f;
            deathExplosionEffect = YellowFx.yellowDeathEffect;
            alwaysShootWhenMoving = true;
            faceTarget = true;
            allowedInPayloads = false;
            createScorch = false;
            createWreck = false;

            Mirror.apply(weapons,
                    YellowWeapons.laserBarrage,
                    YellowWeapons.bulletStorm,
                    YellowWeapons.homingFlares,
                    YellowWeapons.antiMothSpray,
                    YellowWeapons.decimation,
                    YellowWeapons.disruptor,
                    YellowWeapons.ghostCall,
                    YellowWeapons.ghostRain,
                    YellowWeapons.traversal,
                    YellowWeapons.octa,
                    YellowWeapons.energySpheres,
                    YellowWeapons.spearCall,
                    YellowWeapons.blasters,
                    YellowWeapons.gethsemane
            );
        }};

        ghostFlare = new GhostUnitType("ghost-flare"){{
            flying = true;
            health = 37.5f;
            armor = 5f;
            speed = 5.5f;
            accel = 0.08f;
            drag = 0.01f;
            lifetime = 960f;
            lifetimeRnd = 60*10f;
            engineLayer = Layer.effect;
            deathExplosionEffect = YellowFx.ghostDespawnMulti;

            controller = u -> new SwarmOrbiterAI(a -> a.type instanceof YellowUnitType);
        }};
    }
}
