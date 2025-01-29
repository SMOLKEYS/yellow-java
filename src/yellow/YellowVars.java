package yellow;

import arc.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;

public class YellowVars{

    public static WidgetGroup overlayGroup;

    public static void initUI(){
        overlayGroup = new WidgetGroup();

        overlayGroup.setFillParent(true);
        overlayGroup.touchable = Touchable.childrenOnly; // :eyebrow_raised:
        overlayGroup.visible(() -> true);

        Core.scene.add(overlayGroup);
    }
}
