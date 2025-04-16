package yellow;

import arc.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import yellow.ui.fragments.*;
import yellow.util.*;

public class YellowVars{

    public static LoadTextFragment ltfrag;
    public static BlankFragment blankfrag;
    public static NotificationFragment notifrag;

    public static WidgetGroup overlayGroup;

    public static void initUI(){
        ltfrag = new LoadTextFragment();
        blankfrag = new BlankFragment();
        notifrag = new NotificationFragment();

        overlayGroup = new WidgetGroup();

        overlayGroup.setFillParent(true);
        overlayGroup.touchable = Touchable.childrenOnly; // :eyebrow_raised:
        overlayGroup.visible(() -> true);

        Core.scene.add(overlayGroup);

        ltfrag.build(overlayGroup);
        blankfrag.build(overlayGroup);
        notifrag.build(overlayGroup);
    }

    public static float getNotificationTime(){
        return SafeSettings.getFloat("yellow-notification-time", 5,  5);
    }

    public static void setNotificationTime(float time){
        Core.settings.put("yellow-notification-time", time);
    }

}
