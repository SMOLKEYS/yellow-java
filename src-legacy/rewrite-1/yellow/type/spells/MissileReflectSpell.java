package yellow.type.spells;

import arc.math.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.meta.*;

public class MissileReflectSpell extends AreaSpell{

    public double reflectChance = 0.3;
    public float angleRange = 30f;

    public MissileReflectSpell(String name){
        super(name);
    }

    @Override
    public void addStats(){
        super.addStats();
        stats.add(YellowStats.chance, (float) (reflectChance*100), StatUnit.percent);
        stats.add(YellowStats.angleRand, angleRange, StatUnit.degrees);
    }

    @Override
    public void use(Magicc user, SpellEntry spell){
        super.use(user, spell);
        if(user instanceof Unit un){
            Units.nearbyEnemies(un.team(), un.x, un.y, radius, tg -> {
                if(tg instanceof TimedKillUnit tk && Mathf.chance(reflectChance)){
                    tk.team(un.team());
                    tk.vel().inv();
                    tk.lifetime(tk.type().lifetime);
                    tk.rotation((tk.rotation() - 180) + Mathf.range(angleRange));
                    if(tk.controller() instanceof MissileAI ai) ai.shooter = un;
                }
            });
        }
    }
}
