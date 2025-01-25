package yellow.content;

import mindustry.entities.abilities.*;
import mindustry.graphics.*;
import mindustry.type.*;
import yellow.ai.*;
import yellow.entities.abilities.*;
import yellow.entities.units.*;
import yellow.type.weapons.*;

@SuppressWarnings("SpellCheckingInspection")
public class YellowUnitTypes{

    public static UnitType yellow, ghostFlare,

    //WIP
    enverizence, oracle, guardian, attrition, symphony;

    public static void load(){
        yellow = new YellowUnitType(YellowCharacters.nihara, "yellow"){{
            health = 23000f;
            mana = 5500f;
            manaRecovery = 5f;
            lives = 5;
            invFrames = 60*3f;
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

            abilities.addAll(
                    new TeleportAbility(8 * 25f, 8 * 45f),
                    new RegenAbility(){{
                        amount = 2f;
                    }},
                    new ForceFieldAbility(8*3f, 0.55f, 1150f, 60 * 15f)
            );

            Mirror.apply(weapons,
                    YellowWeapons.laserBarrage,
                    YellowWeapons.bulletStorm,
                    YellowWeapons.homingFlares,
                    YellowWeapons.antiMothSpray,
                    YellowWeapons.decimation,
                    YellowWeapons.disruptor,
                    YellowWeapons.ghostCall,
                    YellowWeapons.ghostRain,
                    YellowWeapons.igneous,
                    YellowWeapons.traversal,
                    YellowWeapons.octa,
                    YellowWeapons.energySpheres,
                    YellowWeapons.spearCall,
                    YellowWeapons.blasters,
                    YellowWeapons.gethsemane
            );

            spells.add(YellowSpells.missileInverter, YellowSpells.leftStrafe);
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
            deathExplosionEffect = YellowFx.ghostDespawn3;

            controller = u -> new SwarmOrbiterAI(target -> target.type() == yellow); //target yellow by default
        }};
    }
}
