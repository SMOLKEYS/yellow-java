package yellow.content;

import mindustry.graphics.*;
import mindustry.type.*;
import yellow.ai.*;
import yellow.entities.units.*;

public class YellowUnitTypes{

    public static UnitType yellow, ghostFlare;

    public static void load(){
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

            controller = u -> new SwarmOrbiterAI();
        }};
    }
}
