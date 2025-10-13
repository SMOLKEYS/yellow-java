package yellow.util;

import arc.flabel.*;
import arc.graphics.*;

public class FConfigStore{

    private static boolean keeping;
    private static boolean forceColorMarkupByDefault;
    private static float defaultWaitValue;
    private static float defaultSpeedPerChar;
    private static int charLimitPerFrame;
    private static Color defaultClearColor;

    public static void remember(){
        if(keeping) return;
        forceColorMarkupByDefault = FConfig.forceColorMarkupByDefault;
        defaultWaitValue = FConfig.defaultWaitValue;
        defaultSpeedPerChar = FConfig.defaultSpeedPerChar;
        charLimitPerFrame = FConfig.charLimitPerFrame;
        defaultClearColor = FConfig.defaultClearColor;
        keeping = true;
    }

    public static void restore(){
        if(!keeping) return;
        FConfig.forceColorMarkupByDefault = forceColorMarkupByDefault;
        FConfig.defaultWaitValue = defaultWaitValue;
        FConfig.defaultSpeedPerChar = defaultSpeedPerChar;
        FConfig.charLimitPerFrame = charLimitPerFrame;
        FConfig.defaultClearColor = defaultClearColor;
        keeping = false;
    }

    public static void run(Runnable run){
        remember();
        run.run();
        restore();
    }
}
