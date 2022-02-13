package yellow.world;

import arc.*;
import arc.Events.*;
import arc.util.*;
import arc.util.Timer.*;
import mindustry.*;
import mindustry.game.*;
import java.lang.Integer;

import static arc.Core.*;
import static mindustry.Vars.*;
import static mindustry.game.EventType.*;


public class WorldVars{
    
    //Menu time. Resets whenever you leave the game or enter a world.
    //Unlocks an achivement once 1 hour has passed.
    public int currentSessionMenuTime = settings.getInt("current-session-menu-time");
    //Menu time from all sessions.
    public int allSessionsMenuTimes = settings.getInt("all-sessions-menu-times");
    //Kill count.
    public int killCount = settings.getInt("kill-count");
    //........ehehehe.
    public boolean horny = settings.getBool("horny");
    
    //Prepares all variables *once*.
    public void prepare(){
        
        if(settings.get("prepared", "").toString() == ""){
            settings.put("prepared", true);
            settings.put("current-session-menu-time", Integer(0));
            settings.put("all-sessions-menu-times", Integer(0));
            settings.put("kill-count", Integer(0));
            settings.put("horny", false);
        };
    }
    
    //Starts functionality of all variables. (Timer, Events, etc.)
    public void start(){
        Timer.schedule(() -> {
            if(!state.isGame()){
                settings.put("current-session-menu-time", Integer(currentSessionMenuTime + 1));
            } else {
                settings.put("current-session-menu-time", Integer(0));
            };
            
            settings.put("all-sessions-menu-times", Integer(allSessionsMenuTimes + 1));
        }, 1f, 1f);
        
        Events.on(UnitDestroyEvent.class, () -> settings.put("kill-count", Integer(killCount + 1)));
        Events.on(UnitDrownEvent.class, () -> settings.put("kill-count", Integer(killCount - 1))); //thats their skill issue, not yours
    }
}