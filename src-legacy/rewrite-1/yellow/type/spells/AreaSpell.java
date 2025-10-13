package yellow.type.spells;

import mindustry.world.meta.*;

public class AreaSpell extends Spell{

    public float radius;

    public AreaSpell(String name){
        super(name);
    }

    @Override
    public void addStats(){
        super.addStats();
        stats.add(YellowStats.rangeGeneral, radius/8, StatUnit.blocks);
    }
}
