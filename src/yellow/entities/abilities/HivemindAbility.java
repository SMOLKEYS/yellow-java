package yellow.entities.abilities;

import arc.func.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

public class HivemindAbility extends Ability{
    public int limit;
    public float healthBoost, extraHeal;
    public float damageOnLoss;

    protected static float healthPool;

    public HivemindAbility(int limit, float healthBoost, float damageOnLoss, float extraHeal){
        this.limit = limit;
        this.healthBoost = healthBoost;
        this.damageOnLoss = damageOnLoss;
        this.extraHeal = extraHeal;
    }

    @Override
    public void update(Unit unit){
        healthPool = 0f;

        Boolf<Unit> filt = u -> u.type() == unit.type();
        int count = Groups.unit.count(filt);

        Groups.unit.each(filt, u -> healthPool += u.health);

        Groups.unit.each(filt, u -> u.health = healthPool / count);
    }
}
