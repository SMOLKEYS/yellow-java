package yellow.ui;

import arc.files.*;
import arc.freetype.*;
import arc.freetype.FreeTypeFontGenerator.*;
import arc.graphics.g2d.*;
import mindustry.*;

public class YellowFonts{

    public static Font gothic;

    private static boolean isLoaded;

    public static void load(){
        gothic = new FreeTypeFontGenerator(Vars.tree.get("fonts/msgothic.ttf")).generateFont(new FreeTypeFontParameter(){{
            incremental = true;
        }});
        gothic.getData().markupEnabled = true;

        isLoaded = true;
    }

    public static boolean isLoaded(){
        return isLoaded;
    }
}
