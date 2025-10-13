package yellow.world.meta;

import mindustry.world.meta.*;

public class YellowStats{

    public static Stat lives = new Stat("lives"),
    spells = new Stat("spells"),
    name = new Stat("name"),
    minDst = new Stat("minDst"),
    maxDst = new Stat("maxDst"),
    manaCost = new Stat("manaCost", StatCat.general),
    cooldown = new Stat("cooldown", StatCat.general),
    chance = new Stat("chance"),
    angleRand = new Stat("angleRand"),
    rangeGeneral = new Stat("range", StatCat.general),
    strafeSpeed = new Stat("strafeSpeed"),
    strafeAngle = new Stat("strafeAngle");
}
