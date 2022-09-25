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
import yellow.entities.units.*;
import yellow.entities.units.entity.*;

import static mindustry.Vars.*;

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
            
            weapons.addAll(YellowWeapons.meltdownBurstAttack, YellowWeapons.bullethell, YellowWeapons.airstrikeFlareLauncher, YellowWeapons.antiMothSpray, YellowWeapons.decimation);
        }};
        
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
