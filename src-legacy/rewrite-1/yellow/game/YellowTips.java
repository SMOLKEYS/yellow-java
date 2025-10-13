package yellow.game;

import arc.*;
import arc.struct.*;
import arc.util.*;
import java.util.*;
import mindustry.game.*;

public class YellowTips{

    public static Seq<String> tips = new Seq<>();
    private static String recent = "???";
    private static float timer = 0;
    private static int i = 1;

    public static void load(){
        while(Core.bundle.getOrNull("yellow.tip-" + i) != null){
            tips.add(Core.bundle.get("yellow.tip-header") + Core.bundle.get("yellow.tip-" + i));
            i++;
        }

        Events.run(EventType.Trigger.update, () -> {
            timer += Time.delta;
            if(timer >= YellowVars.getTipTime()){
                recent = tips.select(a -> !Objects.equals(recent, a)).random();
                YellowVars.ui.notifrag.showNotification(recent);
                timer = 0;
            }
        });
    }

    public static void showRandom(){
        recent = tips.select(a -> !Objects.equals(recent, a)).random();
        YellowVars.ui.notifrag.showNotification(recent);
    }
}
