package yellow.entities.abilities;

import arc.util.*;
import arc.util.Timer.*;
import mindustry.content.*;
import mindustry.content.Fx.*;
import mindustry.entities.*;
import mindustry.entities.Effect.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

public class ImmortalAbility extends Ability{
    /** Time it takes for the unit to respawn */
    private float respawnTime;
    /** What effect to call on respawn. */
    private Effect effectOnRespawn;
    /** What tick respawnTime must be on to call effectOnRespawn. Must NOT go above respawnTime. Default 1f. */
    private float respawnEffectCallTime;
    
    public ImmortalAbility(){
        this(1f, Fx.none, 1f);
    }
    
    public ImmortalAbility(float respawnTime){
        this.respawnTime = respawnTime;
    }
    
    public ImmortalAbility(float respawnTime, Effect effectOnRespawn){
        this.respawnTime = respawnTime;
        this.effectOnRespawn = effectOnRespawn;
    }
    
    public ImmortalAbility(float respawnTime, Effect effectOnRespawn, float respawnEffectCallTime){
        this.respawnTime = respawnTime;
        this.effectOnRespawn = effectOnRespawn;
        this.respawnEffectCallTime = respawnEffectCallTime;
    }
    
    @Override
    public void death(Unit unit){
        Timer.schedule(() -> effectOnRespawn.at(unit.x, unit.y), respawnEffectCallTime / 60f);
        Timer.schedule(() -> unit.type.spawn(unit.team, unit.x, unit.y), respawnTime / 60f);
    }
}