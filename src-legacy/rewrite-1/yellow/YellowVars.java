package yellow;

import arc.*;
import arc.files.*;
import arc.fx.*;
import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import arc.util.Timer;
import arc.util.*;
import java.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.graphics.*;
import yellow.content.*;
import yellow.core.*;
import yellow.debug.*;
import yellow.game.*;
import yellow.mods.*;
import yellow.ui.*;
import yellow.util.*;
import yellow.watchdog.*;
import yellow.world.meta.*;

import static yellow.game.YellowEventType.*;

public class YellowVars{

    static Seq<Saves.SaveSlot> foundSlots = new Seq<>(), brokenSlots = new Seq<>();
    static Date date;

    public static YellowUI ui;
    public static Fi extensionDir = Yellow.configDir().child("extensions");

    public static void init(){
        Events.fire(new YellowFirstStageInitializationEvent());
        ui = new YellowUI();

        //let us try
        LoadRenderer renderer = SafeReflect.get(ClientLauncher.class, Vars.platform, "loader");

        Validation.nullPass(renderer, rnd -> {
            FxProcessor fx = SafeReflect.get(rnd, "fx");
            Color color = SafeReflect.get(rnd, "color"),
            colorRed = SafeReflect.get(rnd, "colorRed");
            StringBuilder text = SafeReflect.get(rnd, "assetText");

            boolean disableBloom = SafeSettings.getBool("yellow-disable-bloom", false, false);

            if(disableBloom) Validation.nullPass(fx, FxProcessor::removeAllEffects);
            //Validation.nullPass(color, Color::rand);
            //Validation.nullPass(colorRed, colr -> colr.set(Color.red.cpy()));
            Validation.nullPass(text, tx -> {
                for(int i = 0; i < 5; i++){
                    //int finalI = i;

                    //this only works like 30% of the time but eh whatever

                    Timer.schedule(() -> {
                        //Log.info("tx insert " + finalI + 1);
                        tx.append("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        tx.append("[yellow]yellow mod init[]").append("\n");
                        tx.append("[green]installed at ").append(installTimeAsDate()).append("[]\n");
                        tx.append("[cyan]loading ").append(Yellow.foundExtensions).append(" extension(s)[]").append("\n");
                        if(disableBloom) tx.append("[orange]bloom disabled[]").append("\n");
                        if(Yellow.debug) tx.append("[red]debug mode enabled[]").append("\n");
                        if(Yellow.bypassFailsafe) tx.append("[red]yellow failsafe bypassed[]").append("\n");
                        tx.append("[yellow]welcome to yellow![]").append("\n");
                        tx.append("\n\n");
                    }, (Vars.mobile ? 1 : 5) + i);
                }


            });
        });

        Events.run(ClientLoadEvent.class, () -> {
            ui.init();

            ModPackageBridger.importPackage(
                    "yellow", "yellow.util", "yellow.ui",
                    "yellow.math", "yellow.content", "yellow.cutscene",
                    "yellow.dialogue", "yellow.debug", "yellow.scene.ui.layout",
                    "yellow.ai", "yellow.graphics", "yellow.equality",
                    "yellow.entities.units.entity", "yellow.entities.units", "yellow.entities",
                    "yellow.mods"
            );

            Autoupdater.checkForUpdates(false);

            YellowTips.load();
            YellowSettings.load();
            YellowAchievements.load();
            YellowSpecialNotifications.launchNotif();
            Validation.initYellowChecker();

            if(Core.settings.getBool("yellow-check-unassigned-save-ids", true) && !Vars.control.saves.getSaveSlots().isEmpty()){
                Vars.control.saves.getSaveSlots().each(s -> {
                    if(!s.meta.rules.tags.containsKey("yellow-save-id")) foundSlots.add(s);
                });

                if(foundSlots.size != 0) ui.notifrag.showPersistentNotification(Core.bundle.format("yellow.found-saves", foundSlots.size), () -> {
                    Vars.ui.showConfirm(Core.bundle.format("yellow.found-saves.prompt", foundSlots.size), () -> {
                        foundSlots.each(s -> {
                            if(!SaveIDAssigner.assignId(s)) brokenSlots.add(s);
                        });
                        if(brokenSlots.size != 0){
                            ui.notifrag.showPersistentNotification(Core.bundle.format("yellow.found-saves.results", foundSlots.size - brokenSlots.size, brokenSlots.size));
                        }else{
                            ui.notifrag.showPersistentNotification(Core.bundle.format("yellow.found-saves.noerr-results", foundSlots.size));
                        }
                    });
                });
            }

            if(!Yellow.erroredExtensionList.isEmpty()){
                StringBuilder b = new StringBuilder();
                Yellow.erroredExtensionList.each(e -> {
                    b.append(e.file.name()).append(": ").append(e.exception.getClass().getSimpleName()).append("\n");
                });
                ui.notifrag.showErrorNotification(Core.bundle.format("yellow.extensions-failed", b.toString()));
            }

            ExtensionCore.extensions.each(s -> {
                if(s.meta.enabled()) s.main.clientLoad();
            });

            if(ExtensionCore.extensions.find(e -> e.meta.enabled()) != null) ui.notifrag.showPersistentNotification(Core.bundle.format("yellow.extensions-loaded", ExtensionCore.extensions.select(p -> p.meta.enabled()).size));

            YellowLogic.clientPost();
            if(Yellow.debug) CutsceneTester.load();

            Events.fire(new YellowFinalStageInitializationEvent());
            if(Yellow.launchFile().exists()) Yellow.launchFile().delete();
        });

        YellowLogic.load();

        if(!Core.settings.has("yellow-event-number")){
            Core.settings.put("yellow-event-number", Mathf.random(1, 100));
        }

        Events.fire(new YellowSecondStageInitializationEvent());
    }

    /** Load anything BUT UI here. */
    public static void onImport(){
        if(!Core.settings.has("yellow-install-date")) Core.settings.put("yellow-install-date", System.currentTimeMillis());
    }

    /** Load UI and mod files here. */
    public static void onLaterImport(){
        YellowInitials.begin();
    }

    public static Date installTimeAsDate(){
        if(date == null) return date = new Date(installTime());
        return date;
    }

    public static long installTime(){
        return SafeSettings.getLong("yellow-install-date", 0, 0);
    }

    public static String getUpdateServer(){
        return SafeSettings.getString("yellow-update-server", "SMOLKEYS/yellow-rewritten", "SMOLKEYS/yellow-rewritten");
    }

    public static void setUpdateServer(String server){
        Core.settings.put("yellow-update-server", server);
    }

    public static float getNotificationTime(){
        return SafeSettings.getFloat("yellow-notification-time", 5,  5);
    }

    public static void setNotificationTime(float time){
        Core.settings.put("yellow-notification-time", time);
    }

    public static float getTipTime(){
        return SafeSettings.getFloat("yellow-tip-time", 60*60*5, 60*60*5);
    }

    public static void setTipTime(float time){
        Core.settings.put("yellow-tip-time", time);
    }

    public static void wipeSettings(boolean disable){
        Seq<String> settings = new Seq<>();

        for(String s: Core.settings.keys()){
            if(s.startsWith("yellow-")) settings.add(s);
        }

        settings.each(s -> {
            Core.settings.remove(s);
            Log.info("Erased setting @. Adios!", s);
        });

        if(disable) Core.settings.put("mod-yellow-enabled", false);

        Core.app.exit();
    }
}
