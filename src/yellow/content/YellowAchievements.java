package yellow.content;

import arc.*;
import mindustry.*;

import static arc.Core.*;
import static mindustry.Vars.*;

/** 
 * Achievement list.
 */ 
public class YellowAchievements{
    
    public static String[] achievements = {
        "menu-man",
        "menu-man-2",
        "menu-man-3",
        "homicide",
        "genocide",
        "spudow"
    };
    
    public static String[] modAchievements = {
        "mothicide"
    };
    
    public static String[] optionalAchievements = {
        "menu-man-4"
    };
    
    public static void check(String achievement){8 
        settings.put("yellow-java-achievement-" + achievement + "-unlocked", true);
    }
    
    public static void create(String achievement){
        settings.put("yellow-java-achievement-" + achievement + "-unlocked", false);
    }
    
    public static void showDialogue(String msg, String achievement){
        ui.showInfo(msg + "\n\nUnlocked Achievement: " + achievement);
    }
    
    public static boolean isComplete(String achievement){
        return settings.getBool("yellow-java-achievement-" + achievement + "-unlocked");
    }
}