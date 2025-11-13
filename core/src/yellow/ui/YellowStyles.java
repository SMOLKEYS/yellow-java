package yellow.ui;

import arc.graphics.*;
import arc.scene.ui.Label.*;

public class YellowStyles{

    public static LabelStyle gothicStyle;

    public static void load(){
        gothicStyle = new LabelStyle(){{
            font = YellowFonts.gothic;
            fontColor = Color.white;
        }};
    }
}
