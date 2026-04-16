package yellow.content;

import arc.graphics.*;
import yellow.type.*;

public class YellowGameStyles{

    public static GameStyle bowled, nyanCat, melted, pichuun, bugSpray, bigBugSpray, shocking, strike, bigStrike, biggestStrike, menu;

    public static void load(){
        bowled = new GameStyle("bowled", Color.cyan);
        nyanCat = new GameStyle("nyan-cat", Color.cyan, Color.pink, Color.cyan, Color.pink);
        melted = new GameStyle("melted", Color.gray, Color.scarlet, Color.red, Color.scarlet);
        pichuun = new GameStyle("pichuun", Color.red, Color.white);
        bugSpray = new GameStyle("bug-spray", Color.acid, Color.gray);
        bigBugSpray = new GameStyle("big-bug-spray", Color.acid, Color.gray);
        shocking = new GameStyle("shocking", Color.cyan, Color.white);
        strike = new GameStyle("strike", Color.scarlet, Color.white);
        bigStrike = new GameStyle("big-strike", Color.scarlet, Color.white);
        biggestStrike = new GameStyle("biggest-strike", Color.scarlet, Color.red, Color.white);
        menu = new GameStyle("menu", Color.red, Color.green, Color.blue);
    }
}
