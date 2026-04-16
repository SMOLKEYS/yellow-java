package yellow;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;

public class YellowFirstrun{

    private static final boolean notificationHints = bool("notification-hints");

    private static boolean bool(String name){
        return Core.settings.getBoolOnce("yellow-" + name);
    }

    public static void load(){
        if(notificationHints) showNotificationHints();
    }

    private static boolean delegateNotif(int input, String form){
        if(Yellow.debug) Log.info("delegate notif @", input);
        return switch(input){
            case 3 -> {
                YellowVars.notifrag.showPersistentNotification(Core.bundle.get(form), () -> Vars.ui.showInfo(Core.bundle.format(form + "-1", Mathf.random(9000))));
                yield true;
            }
            case 4 -> {
                YellowVars.notifrag.showErrorNotification(Core.bundle.get(form), new Exception("No actual error here, move along."));
                yield true;
            }
            default -> false;
        };
    }

    public static void showNotificationHints(){
        YellowVars.notifrag.showPersistentNotification(Core.bundle.get("yellow.welcome-notification"));
        int i = 1;
        while(Core.bundle.has("yellow.notification-hint-" + i)){
            int ind = i++;
            Time.run(45f * (i * 0.7f), () -> {
                if(Yellow.debug) Log.info("yellow notif @", ind);

                if(delegateNotif(ind, "yellow.notification-hint-" + ind)) return;
                YellowVars.notifrag.showPersistentNotification(Core.bundle.get("yellow.notification-hint-" + ind));
            });
        }
    }
}
