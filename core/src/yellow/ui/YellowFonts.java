package yellow.ui;

import arc.freetype.*;
import arc.freetype.FreeTypeFontGenerator.*;
import arc.graphics.g2d.*;
import mindustry.*;

public class YellowFonts{

    public static Font msGothic;

    private static boolean isLoaded;

    public static void load(){
        if(isLoaded) return;

        msGothic = new FreeTypeFontGenerator(Vars.tree.get("fonts/msgothic.ttf")).generateFont(new FreeTypeFontParameter(){{
            incremental = true;
        }});
        msGothic.getData().markupEnabled = true;

        isLoaded = true;
    }

    public static boolean isLoaded(){
        return isLoaded;
    }
}
