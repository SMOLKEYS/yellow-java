package yellow.content;

import yellow.type.*;

public class YellowSpells{
    public static Spell heal;

    public static void load(){
        heal = new Spell(){{
            setCost(15f);
        }};
    }
}
