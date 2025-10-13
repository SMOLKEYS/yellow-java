package yellow.content;

public class YellowAchievements{

    public static YellowAchievement installed;

    public static void load(){
        installed = new YellowAchievement("installed");
    }
}
