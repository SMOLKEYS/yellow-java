package yellow.content;

import yellow.type.*;

public class YellowSpells{

    public static Spell fireCircle;

    public static void load(){
        fireCircle = new Spell("fire-circle"){{
           cooldown = 120f;
           onCast = unit -> {

           };
        }};
    }
}
