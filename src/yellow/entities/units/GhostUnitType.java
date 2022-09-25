package yellow.entities.units;

import arc.math.geom.*;
import mindustry.type.UnitType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.entities.Effect;
import mindustry.content.*;
import mindustry.content.Fx.*;
import yellow.content.*;
import yellow.content.YellowFx.*;
import yellow.entities.units.entity.*;

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
    
    @Override
    public Unit create(Team team){
        Unit unit = super.create(team);
        unit.health = unit.maxHealth;
        ((GhostUnitEntity)unit).ghostLifetime = ghostLifetime;
        return unit;
    }
}
