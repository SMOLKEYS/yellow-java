package yellow.util;

import arc.*;
import mindustry.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class YellowUtils{
    
    public static void isEnabled(String modName){
        settings.get("mod-" + modName + "-enabled", "");
    }
    
    public static void createAchievement(String achievementName){
        settings.put("yellow-java-achievement-" + achievementName + "-unlocked", false);
    }
    
    public static void completeAchievement(String achievementName){
        settings.put("yellow-java-achievement-" + achievementName + "-unlocked", true);
    }
    
    public static void showAchievementComplete(String quote, String achievementNonInternalName){
        ui.showInfo(quote + "\n\nUnlocked Achievement: " + achievementNonInternalName);
    }
    
    public static boolean isComplete(String achievementName){
        return settings.getBool("yellow-java-achievement-" + achievementName + "-unlocked");
    }
}