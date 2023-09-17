package yellow.content;

import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import yellow.type.*;

public class YellowSpells{

    public static Spell fireCircle;

    public static void load(){
        fireCircle = new Spell("fire-circle"){{
           cooldown = 180;
           onCast = unit -> {
               Units.nearby(null, unit.x, unit.y, 8*20, out -> {
                   if(out.team() != unit.team()) out.apply(StatusEffects.burning, 60f * Mathf.random(5, 15));
               });
           };
        }};
    }
}
