package yellow.type.spell;

import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;

public class StatusEffectInfliction extends CommonCastComponent{
    /** The effect to be qpplied. */
    public StatusEffect effect = StatusEffects.none;
    /** How long the effects applied should last, in ticks. */
    public float minTime, maxTime;

    @Override
    public void apply(Unit caster){
        Units.nearby(null, caster.x, caster.y, range, out -> {
            if(out.team() != caster.team()){
                out.apply(effect, Mathf.random(minTime, maxTime));
            };
        });
    }
}
