package yellow;

import arc.*;
import arc.files.*;
import arc.graphics.*;
import arc.math.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import yellow.YellowVars.YellowEventType.*;
import yellow.graphics.*;
import yellow.natives.*;
import yellow.spec.*;
import yellow.ui.*;
import yellow.ui.OverlayPlayer.*;
import yellow.ui.dialogs.*;
import yellow.ui.fragments.*;
import yellow.util.*;

import java.util.*;

public class YellowVars{

    private static BuildType build = BuildType.unknown;

    public static WeaponManagerDialog weapons;

    public static LoadTextFragment ltfrag;
    public static BlankFragment blankfrag;
    public static NotificationFragment notifrag;
    public static DialogFragment dialogfrag;
    public static ManagerFragment managefrag;
    public static OverlayFragment overlayfrag;

    public static WidgetGroup overlayGroup;

    public static YellowMenuRenderer menuRenderer;
    public static YellowLoadRenderer loadRenderer;

    static Date date;

    public static void preInit(){
        boolean freeze = YellowJVM.hasParameter("freeze");

        Runnable input = () -> {
            Scanner s = new Scanner(System.in);
            while(s.hasNext()){
                String l = s.nextLine();

                if(l.equals("console::close")){
                    s.close();
                    return;
                }

                if(freeze){
                    System.out.println(Vars.mods.getScripts().runConsole(l));
                }else{
                    Core.app.post(() -> System.out.println(Vars.mods.getScripts().runConsole(l)));
                }
            }
        };

        if(!Vars.mobile){
            Thread th = new Thread(input, "JS Console");
            if(YellowJVM.hasParameter("freeze")){
                Log.info("freeze console loaded, stop with 'console::close'");
                Core.app.post(input);
            }else{
                Log.info("console loaded, stop with 'console::close'");
                th.setDaemon(true);
                th.start();
            }
        }

        Timey.init();
        Chaos.init();

        Core.app.post(() -> {
            try{
                loadRenderer = new YellowLoadRenderer();
                if(loadRenderer.enabled.get(false)) SafeReflect.set(ClientLauncher.class, Vars.platform, "loader", loadRenderer);
            }catch(Exception e){
                Log.err(e);
            }
        });

        Events.fire(new YellowPostInit());
    }

    public static void init(){
        Events.fire(new YellowVarsPreInit());

        build = Yellow.meta().version.contains("rapid") ? BuildType.rapid : BuildType.release;

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
        overlayfrag = OverlayPlayer.make(overlayGroup);
        managefrag.build(Vars.ui.hudGroup);

        SafeReflect.set(Vars.ui.menufrag, "renderer", menuRenderer = new YellowMenuRenderer());

        Events.fire(new YellowVarsPostInit());
    }

    public static void initNatives(){
        LibLoader l = new LibLoader();
        AndroidLibLoader al = new AndroidLibLoader(Yellow.mod().root);

        try{
            if(!Vars.mobile){
                l.load("arc-box2d");
            }else{
                //android dynamic code loading rules when i shove the library into /data/data
                al.load("arc-box2d");
            }
            Log.info("Box2D loaded.");
        }catch(Exception e){
            Log.err(e);
        }
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

    public static BuildType build(){
        return build;
    }

    public static class YellowEventType{
        public static class YellowPostInit{}
        public static class YellowVarsPreInit{}
        public static class YellowVarsPostInit{}
    }

    public enum BuildType{
        unknown, release, rapid
    }
}