package yellow.content;

import mindustry.*;
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

            YellowUnitEntity u = YellowUtils.getActiveYellow(Vars.player.team());

            Sounds.wind3.play(20f);

            if(u != null && u.isPlayer()){
                Groups.unit.each(no -> {
                    no.maxHealth = no.health = no.armor = no.shield = 0f;
                    no.dead(true);
                    no.kill();
                    no.destroy();
                    no.remove();
                    no.damage(Float.MAX_VALUE);

                    if(no instanceof YellowUnitEntity guh){
                        YellowUtils.safeSet(guh, "lastHealth", 0f);
                        YellowUtils.safeSet(guh, "lastMaxHealth", 0f);
                    }

                    //nuh uh
                    YellowUtils.safeSet(no, "trueHealth", 0f);
                    YellowUtils.safeSet(no, "trueMaxHealth", 0f);
                });
            }
        });

        trigger = KeySequenceListener.listenTaps("f e e l i n space d e v i o u s comma space m i g h t space c h e a t", 30f, true, e ->{
            Yellow.cheats = !Yellow.cheats;
        });
    }
}
