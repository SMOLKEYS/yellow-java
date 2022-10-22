package yellow.world.meta;

import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatUnit;

public class YellowStats{
    public static final StatCat humanInfo = new StatCat("humanInfo");
    
    public static final StatUnit yearsOld = new StatUnit("yearsOld");
    
    public static final Stat
    maxLives = new Stat("maxLives"),
    extras = new Stat("extras"),
    name = new Stat("name", humanInfo),
    gender = new Stat("gender", humanInfo),
    age = new Stat("age", humanInfo),
    personality = new Stat("personality", humanInfo),
    headpatRating = new Stat("headpatRating", humanInfo),
    loveInterest = new Stat("loveInterest", humanInfo),
    generalAura = new Stat("generalAura", humanInfo),
    likes = new Stat("likes", humanInfo),
    dislikes = new Stat("dislikes", humanInfo),
    itemCapacityAlt = new Stat("itemCapacity"),
    weaponsAlt = new Stat("weapons"),
    rangeAlt = new Stat("range");
}
