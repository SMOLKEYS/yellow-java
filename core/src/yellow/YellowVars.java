package yellow;

import arc.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import yellow.async.*;
import yellow.core.YellowEventType.*;
import yellow.core.*;
import yellow.cutscene.*;
import yellow.entities.*;
import yellow.graphics.*;
import yellow.spec.*;
import yellow.ui.*;
import yellow.ui.OverlayPlayer.*;
import yellow.ui.dialogs.*;
import yellow.ui.fragments.*;
import yellow.util.*;

import java.util.*;

public class YellowVars{

    private static BuildType build = BuildType.unknown;

    public static YellowLogic logic;

    public static WeaponManagerDialog weapons;

    public static LoadTextFragment ltfrag;
    public static BlankFragment blankfrag;
    public static NotificationFragment notifrag;
    public static DialogFragment dialogfrag;
    public static ManagerFragment managefrag;
    public static OverlayFragment overlayfrag;

    public static Cutscenes cutscenes;

    public static WidgetGroup overlayGroup;

    public static YellowMenuRenderer menuRenderer;
    public static YellowLoadRenderer loadRenderer;

    static Date date;

    public static void preInit(){
        Timey.init();
        Chaos.init();

        preInitComponents();

        Events.fire(new YellowPostInit());
    }

    public static void preInitComponents(){
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

        Vars.asyncCore.processes.add(
                new PhysicsHandler<>(YellowGroups.physics)
        );

        Core.app.post(() -> {
            try{
                loadRenderer = new YellowLoadRenderer();
                if(loadRenderer.enabled.get(false)) SafeReflect.set(ClientLauncher.class, Vars.platform, "loader", loadRenderer);
            }catch(Exception e){
                Log.err(e);
            }
        });
    }

    public static void init(){
        Events.fire(new YellowVarsPreInit());

        build = Yellow.meta().version.contains("rapid") ? BuildType.rapid : BuildType.release;

        logic = new YellowLogic();

        weapons = new WeaponManagerDialog();

        ltfrag = new LoadTextFragment();
        blankfrag = new BlankFragment();
        notifrag = new NotificationFragment();
        dialogfrag = new DialogFragment();
        managefrag = new ManagerFragment();

        cutscenes = new Cutscenes();
        cutscenes.init();

        overlayGroup = new WidgetGroup();

        overlayGroup.setFillParent(true);
        overlayGroup.touchable = Touchable.childrenOnly;
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
        /*
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
         */
    }

    public static void onImport(){
        if(!YellowSettingValues.installDate.exists()) YellowSettingValues.installDate.set(System.currentTimeMillis());
    }

    public static Date installedAt(){
        if(date == null) return date = new Date(YellowSettingValues.installDate.get());
        return date;
    }

    public static BuildType build(){
        return build;
    }

    public enum BuildType{
        unknown, release, rapid
    }
}