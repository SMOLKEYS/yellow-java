package yellow;

import arc.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import mindustry.*;
import yellow.combat.*;
import yellow.ui.*;
import yellow.ui.dialogs.*;
import yellow.ui.fragments.*;
import yellow.util.*;

import java.util.*;

public class YellowVars{

    public static WeaponManagerDialog weapons;

    public static LoadTextFragment ltfrag;
    public static BlankFragment blankfrag;
    public static NotificationFragment notifrag;
    public static DialogFragment dialogfrag;
    public static ManagerFragment managefrag;

    public static WidgetGroup overlayGroup;

    public static ChaosRenderer chaosRenderer;

    public static CombatMode combat;

    static Date date;

    public static void init(){
        weapons = new WeaponManagerDialog();

        ltfrag = new LoadTextFragment();
        blankfrag = new BlankFragment();
        notifrag = new NotificationFragment();
        dialogfrag = new DialogFragment();
        managefrag = new ManagerFragment();

        overlayGroup = new WidgetGroup();

        overlayGroup.setFillParent(true);
        overlayGroup.touchable = Touchable.childrenOnly; // :eyebrow_raised:
        overlayGroup.visible(() -> true);

        overlayGroup.update(() -> overlayGroup.toFront());

        Core.scene.add(overlayGroup);

        ltfrag.build(overlayGroup);
        blankfrag.build(overlayGroup);
        notifrag.build(overlayGroup);
        dialogfrag.build(overlayGroup);
        managefrag.build(Vars.ui.hudGroup);

        SafeReflect.set(Vars.ui.menufrag, "renderer", chaosRenderer = new ChaosRenderer());

        combat = new CombatMode();
        combat.init();
    }

    public static void onImport(){
        if(!Core.settings.has("yellow-install-date")) Core.settings.put("yellow-install-date", System.currentTimeMillis());
    }

    public static long installTime(){
        if(!Core.settings.has("yellow-install-date")) Core.settings.put("yellow-install-date", System.currentTimeMillis());
        return SafeSettings.getLong("yellow-install-date", 0, 0);
    }

    public static Date installTimeAsDate(){
        if(date == null) return date = new Date(installTime());
        return date;
    }

    public static float getNotificationTime(){
        return SafeSettings.getFloat("yellow-notification-time", 5,  5);
    }

    public static void setNotificationTime(float time){
        Core.settings.put("yellow-notification-time", time);
    }

}
