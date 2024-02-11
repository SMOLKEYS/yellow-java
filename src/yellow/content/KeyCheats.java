package yellow.content;

import arc.util.*;
import mindustry.gen.*;
import yellow.*;
import yellow.entities.units.entity.*;
import yellow.input.*;
import yellow.util.*;

public class KeyCheats{

    public static KeySequenceListener<?> nuhUh, trigger;

    public static void load(){
         nuhUh = KeySequenceListener.listenTaps("n u h space u h", 30f, true, e -> {
            if(!Yellow.cheats) return;

            YellowUnitEntity u = YellowUtils.getActiveYellow();

            Sounds.wind3.play(20f);

            if(u != null && u.isPlayer()){
                Groups.unit.each(no -> {
                    no.maxHealth = no.health = no.armor = no.shield = 0f;
                    no.dead(true);
                    no.kill();
                    no.destroy();
                    no.remove();
                    no.damage(Float.MAX_VALUE);

                    //here in nuhuh country, we do not have favorites
                    //and so god help me because i mean it in every way possible
                    try{
                        Reflect.set(no, "trueHealth", 0f);
                        Reflect.set(no, "trueMaxHealth", 0f);
                    }catch(Exception ignored){

                    }
                });
            }
        });

        trigger = KeySequenceListener.listenTaps("f e e l i n space d e v i o u s comma space m i g h t space c h e a t", 30f, true, e ->{
            Yellow.cheats = !Yellow.cheats;
        });
    }
}
