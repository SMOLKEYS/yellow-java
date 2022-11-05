package yellow.entities.units;

import arc.math.geom.*;
import mindustry.entities.*;
import mindustry.type.*;
import yellow.content.*;
import yellow.entities.units.entity.*;

public class GhostUnitType extends UnitType{
    /** Unit lifetime in ticks. */
    public float ghostLifetime = 900f;
    /** Despawn effect. */
    public Effect despawnEffect = YellowFx.ghostDespawnMulti;
    /** Despawn effect offset. */
    public Vec2 despawnEffectOffset = new Vec2();
    
    
    public GhostUnitType(String name){
        super(name);
        constructor = GhostUnitEntity::new;
    }
}
