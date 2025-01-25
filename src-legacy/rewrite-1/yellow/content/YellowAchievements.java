package yellow.content;

import yellow.game.*;

public class YellowAchievements{

    public static YellowAchievement installed;

    public static void load(){
        installed = new YellowAchievement("installed");
    }
}
