package yellow.content;

import arc.audio.*;
import mindustry.*;

public class YellowSounds{

    public static Sound gethsemaneExplosion;
    public static Sound bowlingStrike;

    public static Sound DAMN, chicken;

    public static void load(){
        gethsemaneExplosion = ls("gethsemaneExplosion");
        bowlingStrike = ls("bowlingStrike");

        DAMN = ls("damn");
        chicken = ls("chicken");
    }

    private static Sound ls(String nm){
        return Vars.tree.loadSound(nm);
    }
}
