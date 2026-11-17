package yellow.content;

import ent.anno.Annotations;
import mindustry.gen.TimedKillc;
import mindustry.graphics.*;
import mindustry.type.*;
import yellow.ai.*;
import yellow.entities.abilities.*;
import yellow.gen.*;
import yellow.type.unit.*;
import yellow.type.weapons.*;

public class YellowUnitTypes{

    public static @Annotations.EntityDef(value = {Yellowc.class, WeaponMasterc.class, SingleInstancec.class}) UnitType yellow;
    public static @Annotations.EntityDef(value = {Ghostc.class, TimedKillc.class}) UnitType ghostFlare;

    public static void load(){

        yellow = new YellowUnitType("yellow"){
            {
                health = 2000f;
                flying = true;
                hideDetails = false;
                armor = 17f;
                speed = 4f;
                accel = 0.08f;
                drag = 0.03f;
                range = 8 * 70f;
                maxRange = 8 * 90f;
                mineSpeed = 500f;
                mineTier = 500;
                itemCapacity = 850;
                buildSpeed = 950f;
                deathExplosionEffect = YellowFx.yellowDeathEffect;
                alwaysShootWhenMoving = true;
                faceTarget = true;
                createScorch = false;
                createWreck = false;

                Mirror.apply(weapons,
                        YellowWeapons.laserBarrage,
                        YellowWeapons.card1,
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
                        YellowWeapons.gethsemane,
                        YellowWeapons.contingency
                );
            }};

        ghostFlare = new GhostUnitType("ghost-flare"){{
            flying = true;
            health = 37.5f;
            armor = 5f;
            speed = 5.5f;
            accel = 0.08f;
            drag = 0.01f;
            lifetime = 60*16f;
            lifetimeRnd = 60*10f;
            engineLayer = Layer.effect;
            deathExplosionEffect = YellowFx.ghostDespawnMulti;

            controller = u -> new SwarmOrbiterAI(a -> a.type instanceof YellowUnitType);

            abilities.add(new HivemindAbility(-1, 25f, 10f, 20f));
        }};

        EntityRegistry.registerUnits();
    }
}
