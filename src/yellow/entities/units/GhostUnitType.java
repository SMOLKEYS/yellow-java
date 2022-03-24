package yellow.entities.units;

import arc.util.*;
import arc.util.Time.*;
import arc.math.geom.Vec2;
import mindustry.type.UnitType;
import mindustry.gen.Unit;
import mindustry.entities.Effect;
import mindustry.content.*;
import mindustry.content.Fx.*;
import yellow.content.*;
import yellow.content.YellowFx.*;
import yellow.entities.units.entity.*;

public class GhostUnitType extends UnitType{
    /** Unit lifetime in ticks. */
    public float lifetime = 900f;
    /** Despawn effect. */
    public Effect despawnEffect = YellowFx.ghostDespawn;
    /** Despawn effect offswt. */
    public Vec2 despawnEffectOffset = new Vec2(0, 0);
    
    public GhostUnitType(String name){
        super(name);
        constructor = GhostUnitEntity::new;
    }
    
    @Override
    public void update(Unit unit){
        
        GhostUnitEntity ghost = ((GhostUnitEntity)unit);
        ghost.lifetime -= Time.delta;
        ghost.clampLifetime();
        if(ghost.lifetime <= 0f){
            ghost.remove();
            despawnEffect.at(unit.x + despawnEffectOffset.x, unit.y + despawnEffectOffset.y);
        };
    }
}
