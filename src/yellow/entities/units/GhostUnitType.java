package yellow.entities.units;

import arc.math.geom.Vec2;
import mindustry.entities.Effect;
import mindustry.type.UnitType;
import yellow.content.YellowFx;
import yellow.entities.units.entity.GhostUnitEntity;

public class GhostUnitType extends UnitType{
    /** Unit lifetime in ticks. */
    public float ghostLifetime = 900f;
    /** Despawn effect. */
    public Effect despawnEffect = YellowFx.ghostDespawn;
    /** Despawn effect offset. */
    public Vec2 despawnEffectOffset = new Vec2(0, 0);
    
    
    public GhostUnitType(String name){
        super(name);
        constructor = GhostUnitEntity::new;
    }
}
