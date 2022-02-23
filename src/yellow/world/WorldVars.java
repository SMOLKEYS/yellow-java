package yellow.world;

import arc.*;
import arc.Events.*;
import arc.util.*;
import arc.util.Timer.*;
import arc.util.async.*;
import arc.util.async.Threads.*;
import mindustry.*;
import mindustry.game.*;
import yellow.util.*;
import yellow.content.*;
import yellow.content.YellowAchievements.*;


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
    
    public static boolean anticheat(){
        return settings.getBool("yellow-java-anticheat");
    }
    
    //Prepares all variables *once*. Also acts as a reset trigger.
    public static void prepare(){
        
        if(settings.getBool("yellow-java-prepared") != true){
            settings.put("yellow-java-prepared", true);
            settings.put("yellow-java-anticheat", true);
            settings.put("yellow-java-current-session-menu-time", 0);
            settings.put("yellow-java-all-sessions-menu-times", 0);
            settings.put("yellow-java-kill-count", 0);
            //garbage code, god help me
            for(int i = 0; i < YellowAchievements.achivements.length; i++){
                YellowAchievements.create(YellowAchievements.achievements[i]);
            };
            for(int i = 0; i < YellowAchievements.modAchivements.length; i++){
                YellowAchievements.create(YellowAchievements.modAchivements[i]);
            };
            for(int i = 0; i < YellowAchievements.optionalAchivements.length; i++){
                YellowAchievements.create(YellowAchievements.optionalAchievements[i]);
            };
        };
    }
    
    //Starts functionality of all variables. (Timer, Events, etc.)
    public static void start(){
        Timer.schedule(() -> {
            if(!state.isGame()){
                settings.put("yellow-java-current-session-menu-time", currentSessionMenuTime() + 1);
                settings.put("yellow-java-all-sessions-menu-times", allSessionsMenuTimes() + 1);
            } else {
                settings.put("yellow-java-current-session-menu-time", 0);
            };
            
            if(currentSessionMenuTime() > 3600 && !YellowAchievements.isComplete("menu-man")){
                YellowAchievements.showDialogue("Uhhh... you've been in this menu for an hour now. Are you ok or just bored?", "Menu Man");
                YellowAchievements.check("menu-man");
            };
            
            if(currentSessionMenuTime() > 10800 && !YellowAchievements.isComplete("menu-man-2")){
                YellowAchievements.showDialogue("...it's been 3 hours, did you just leave the game open?", "Menu Man 2");
                YellowAchievements.check("menu-man-2");
            };
            
            if(currentSessionMenuTime() > 43200 && !YellowAchievements.isComplete("menu-man-3")){
                YellowAchievements.showDialogue("...it's been 12 hours... are you even on your device right now?", "Menu Man 3");
                YellowAchievements.check("menu-man-3");
            };
            
            if(currentSessionMenuTime() > 1814400 && !YellowAchievements.isCompelete("menu-man-4")){
                YellowAchievements.showDialogue("...", "Menu Man 4");
                YellowAchievements.check("menu-man-4");
            };
            
            if(killCount() > 500 && !YellowAchievements.isComplete("homicide") && state.isCampaign()){
                YellowAchievements.showDialogue("You mad fuck.", "Homicide");
                YellowAchievements.check("homicide");
            };
            
            if(currentSessionMenuTime() > allSessionsMenuTimes() && anticheat() == true){
                Threads.throwAppException(new IllegalStateException("Noooo! Don't do that! (CSMT value higher than ASMT value: " + currentSessionMenuTime() + " > " + allSessionsMenuTimes() + ")"));
            };
        }, 1f, 1f, -1);
        
        Events.run(UnitDestroyEvent.class, () -> settings.put("yellow-java-kill-count", killCount() + 1));
        Events.run(UnitDrownEvent.class, () -> settings.put("yellow-java-kill-count", killCount() - 1)); //thats their skill issue, not yours
    }
}