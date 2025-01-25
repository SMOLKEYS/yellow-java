package yellow.type.spells;

import arc.math.*;
import mindustry.gen.*;
import mindustry.world.meta.*;
import yellow.comp.*;
import yellow.entities.units.*;
import yellow.type.*;
import yellow.world.meta.*;

public class StrafeSpell extends Spell{

    public float strafeAngle;
    public float strafeSpeed;
    public float angleRnd;

    public StrafeSpell(String name){
        super(name);
    }

    @Override
    public void addStats(){
        super.addStats();

        stats.add(YellowStats.strafeAngle, strafeAngle, StatUnit.degrees);
        stats.add(YellowStats.strafeSpeed, strafeSpeed, YellowStatUnits.timesUnit);
    }

    @Override
    public void use(Magicc user, SpellEntry spell){
        if(user instanceof Unit u) u.vel().trns(u.rotation + strafeAngle + Mathf.range(angleRnd), u.speed() * strafeSpeed);
    }
}
