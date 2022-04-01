package yellow.entities.units;

import arc.math.*;
import arc.math.Mathf.*;
import arc.util.*;
import arc.util.Time.*;
import arc.math.geom.Vec2;
import arc.graphics.*;
import arc.graphics.g2d.TextureRegion;
import mindustry.type.UnitType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.entities.Effect;
import mindustry.content.*;
import mindustry.content.Fx.*;
import yellow.content.*;
import yellow.content.YellowFx.*;
import yellow.entities.units.entity.*;
import yellow.interactions.*;
import yellow.interactions.Responses.*;
import yellow.interactions.ChatBubble.*;

public class GhostUnitType extends UnitType{
    /** Unit lifetime in ticks. */
    public float lifetime = 900f;
    /** Despawn effect. */
    public Effect despawnEffect = YellowFx.ghostDespawn;
    /** Despawn effect offset. */
    public Vec2 despawnEffectOffset = new Vec2(0, 0);
    
    /** Whether to enable crack textures or glow effects. */
    public boolean cracks = false, glow = false;
    /** Crack texture that appears on the unit as it gets closer to the end of its lifetime. */
    public TextureRegion crackRegion;
    
    /**
     * TODO? shard regions on unit despawn?
     * public TextureRegion[] shardRegions;
     */
    
    
    public GhostUnitType(String name){
        super(name);
        constructor = GhostUnitEntity::new;
    }
    
    @Override
    public void update(Unit unit){
        
        if(Mathf.chance(0.009)){
            ChatBubble.createBubble(unit, "[red]" + Responses.responses[Mathf.floor(Mathf.random() * Responses.responses.length)]);
        };
        
        GhostUnitEntity ghost = ((GhostUnitEntity)unit);
        ghost.lifetime -= Time.delta;
        ghost.clampLifetime();
        if(ghost.lifetime <= 0f){
            ghost.remove();
            despawnEffect.at(unit.x + despawnEffectOffset.x, unit.y + despawnEffectOffset.y);
        };
        
        super.update(unit);
    }
    
    @Override
    public Unit create(Team team){
        Unit unit = super.create(team);
        unit.health = unit.maxHealth;
        ((GhostUnitEntity)unit).lifetime = lifetime;
        return unit;
    }
}
