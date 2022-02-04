package yellow.entities.abilities;

import arc.util.*;
import arc.util.Timer.*;
import arc.math.geom.*;
import arc.math.geom.Vec2.*;
import mindustry.content.*;
import mindustry.content.Fx.*;
import mindustry.entities.*;
import mindustry.entities.Effect.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

public class RespawnAbility extends Ability{
    /** Time it takes for the unit to respawn. */
    private float respawnTime = 1f;
    /** What effect to call on respawn. */
    private Effect effectOnRespawn = Fx.none;
    /** What tick respawnTime must be on to call effectOnRespawn. Must NOT go above respawnTime. Default 1f. */
    private float respawnEffectCallTime = 1f;
    /** Respawn/Respawn effect offset. */
    private Vec2 respawnOffsetPos = new Vec2(0, 0);
    private Vec2 effectOffsetPos = new Vec2(0, 0);
    
    public RespawnAbility(){
        this(1f, Fx.none, 1f, new Vec2(0, 0), new Vec2(0, 0));
    }
    
    public RespawnAbility(float respawnTime){
        this.respawnTime = respawnTime;
    }
    
    public RespawnAbility(float respawnTime, Effect effectOnRespawn){
        this.respawnTime = respawnTime;
        this.effectOnRespawn = effectOnRespawn;
    }
    
    public RespawnAbility(float respawnTime, Effect effectOnRespawn, float respawnEffectCallTime){
        this.respawnTime = respawnTime;
        this.effectOnRespawn = effectOnRespawn;
        this.respawnEffectCallTime = respawnEffectCallTime;
    }
    
    public RespawnAbility(float respawnTime, Effect effectOffsetPos, float respawnEffectCallTime, Vec2 respawnOffsetPos, Vec2 effectOffsetPos¹){
        this.respawnTime = respawnTime;
        this.effectOnRespawn = effectOnRespawn;
        this.respawnEffectCallTime = respawnEffectCallTime;
        this.respawnOffsetPos = respawnOffsetPos;
        this.effectOffsetPos = effectOffsetPos;
    }
    
    @Override
    public void death(Unit unit){
        Timer.schedule(() -> effectOnRespawn.at(unit.x + effectOffsetPos.x, unit.y + effectOffsetPos.y), respawnEffectCallTime / 60f);
        Timer.schedule(() -> unit.type.spawn(unit.team, unit.x + respawnOffsetPos.x, unit.y + respawnOffsetPos.y), respawnTime / 60f);
    }
}