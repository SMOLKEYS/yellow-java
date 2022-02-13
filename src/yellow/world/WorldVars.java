package yellow.world;

import arc.*;
import arc.Events.*;
import arc.util.*;
import arc.util.Timer.*;
import mindustry.*;
import mindustry.game.*;

import static arc.Core.*;
import static mindustry.Vars.*;
import static mindustry.game.EventType.*;


public class WorldVars{
    
    //Menu time. Resets whenever you leave the game or enter a world.
    //Unlocks an achivement once 1 hour has passed.
    public static int currentSessionMenuTime(){
        return settings.getInt("current-session-menu-time");
    }
    //Menu time from all sessions.
    public static int allSessionsMenuTimes(){
        return settings.getInt("all-sessions-menu-times");
    }
    //Kill count.
    public static int killCount(){
        return settings.getInt("kill-count");
    }
    //........ehehehe.
    public static boolean horny(){
        return settings.getBool("horny");
    }
    
    //Prepares all variables *once*.
    public static void prepare(){
        
        if(settings.get("prepared", "").toString() == ""){
            settings.put("prepared", true);
            settings.put("current-session-menu-time", 0);
            settings.put("all-sessions-menu-times", 0);
            settings.put("kill-count", 0);
            settings.put("horny", false);
        };
    }
    
    //Starts functionality of all variables. (Timer, Events, etc.)
    public static void start(){
        Timer.schedule(() -> {
            if(!state.isGame()){
                settings.put("current-session-menu-time", currentSessionMenuTime() + 1);
                settings.put("all-sessions-menu-times", allSessionsMenuTimes() + 1);
            } else {
                settings.put("current-session-menu-time", 0);
            };
            
        }, 1f, 1f, -1);
        
        Events.run(UnitDestroyEvent.class, () -> settings.put("kill-count", killCount() + 1));
        Events.run(UnitDrownEvent.class, () -> settings.put("kill-count", killCount() - 1)); //thats their skill issue, not yours
    }
}