package yellow.world.meta;

import mindustry.type.UnitType;
import mindustry.world.meta.*;
import yellow.content.YellowUnitTypes;

public class YellowStats{
    public static StatCat humanInfo = new StatCat("humanInfo");
    
    public static Stat
    maxLives = new Stat("maxLives"),
    extras = new Stat("extras"),
    gender = new Stat("gender", humanInfo),
    age = new Stat("age", humanInfo),
    personality = new Stat("personality", humanInfo),
    headpatRating = new Stat("headpatRating", humanInfo),
    loveInterest = new Stat("loveInterest", humanInfo),
    generalAura = new Stat("generalAura", humanInfo);

}
