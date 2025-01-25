package yellow.world.meta;

import mindustry.world.meta.*;

//agone
public class YellowStats{
    public static final StatCat humanInfo = new StatCat("humanInfo");
    
    public static final StatUnit yearsOld = new StatUnit("yearsOld");

    public static final Stat
    maxLives = new Stat("maxLives"),
    extras = new Stat("extras"),
    name = new Stat("name", humanInfo),
    gender = new Stat("gender", humanInfo),
    age = new Stat("age", humanInfo),
    affinity = new Stat("affinity", humanInfo),
    itemCapacityAlt = new Stat("itemCapacity"),
    weaponsAlt = new Stat("weapons"),
    rangeAlt = new Stat("range");
}
