package yellow.world;

import arc.*;
import arc.Events.*;
import arc.util.*;
import arc.util.Timer.*;
import mindustry.*;
import mindustry.game.*;
import yellow.util.*;

import static arc.Core.*;
import static mindustry.Vars.*;
import static mindustry.game.EventType.*;
import static yellow.util.YellowUtils.*;


public class WorldVars{
    
    //Menu time. Resets whenever you leave the game or enter a world.
    //Unlocks an achivement once 1 hour has passed.
    public static int currentSessionMenuTime(){
        return settings.getInt("yellow-java-current-session-menu-time");
    }
    //Menu time from all sessions.
    public static int allSessionsMenuTimes(){
        return settings.getInt("yellow-java-all-sessions-menu-times");
    }
    //Kill count.
    public static int killCount(){
        return settings.getInt("yellow-java-kill-count");
    }
    //........ehehehe.
    public static boolean horny(){
        return settings.getBool("yellow-java-horny");
    }
    
    //Prepares all variables *once*.
    public static void prepare(){
        
        if(settings.getBool("yellow-java-prepared") != true){
            settings.put("yellow-java-prepared", true);
            settings.put("yellow-java-current-session-menu-time", 0);
            settings.put("yellow-java-all-sessions-menu-times", 0);
            settings.put("yellow-java-kill-count", 0);
            settings.put("yellow-java-horny", false);
            createAchievement("menu-man");
            createAchievement("menu-man-2");
        };
    }
    
    //Starts functionality of all variables. (Timer, Events, etc.)
    public static void start(){
        Timer.schedule(() -> {
            if(!state.isGame()){
                settings.put("yellow-java-current-session-menu-time", currentSessionMenuTime() + 1);
                settings.put("yellow-java-all-sessions-menu-times", allSessionsMenuTimes() + 1);
            } else {
                settings.put("current-session-menu-time", 0);
            };
            
            if(currentSessionMenuTime() < 3600 && !isComplete("menu-man")){
                showAchievementComplete("Uhhh... you've been in this menu for an hour now. Are you ok or just bored?", "Menu Man");
                completeAchievement("menu-man");
            };
            
            if(currentSessionMenuTime() < 10800 && !isComplete("menu-man-2")){
                showAchievementComplete("...it's been 3 hours, did you just leave the game open?", "Menu Man 2");
                completeAchievement("menu-man-2");
            };
        }, 1f, 1f, -1);
        
        Events.run(UnitDestroyEvent.class, () -> settings.put("yellow-java-kill-count", killCount() + 1));
        Events.run(UnitDrownEvent.class, () -> settings.put("yellow-java-kill-count", killCount() - 1)); //thats their skill issue, not yours
    }
}