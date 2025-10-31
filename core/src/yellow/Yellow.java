package yellow;

import arc.*;
import arc.files.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.mod.*;
import yellow.YellowVars.YellowEventType.*;
import yellow.content.*;
import yellow.gen.*;
import yellow.js.*;
import yellow.spec.*;
import yellow.ui.*;
import yellow.util.*;

import java.util.*;

//third rewrite note: the more i do this the more i wanna throw my laptop against a wall
@SuppressWarnings({
        "unused", //oh and shut the fuck up java
        "GrazieInspection" //you too intellij
})
public class Yellow extends Mod{

    static final SettingBoundVariable<Long> lastFileDate = new SettingBoundVariable<>("yellow-debug-lastfiledate", 1997L);

    public static final boolean debug = YellowJVM.hasParameter("yellow-debug", /*() -> Objects.equals(OS.username, "smol"),*/ str -> {
        Log.infoTag(str, "Yellow debug mode enabled.");

        Events.run(YellowVarsPostInit.class, () -> {
            Date d = new Date(lastFileDate.get());
            Date nd = new Date(mod().file.lastModified());

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Yellow debug info:\n\nPast file time: @\n\nLoaded file: [gold]@[]\nFile time: @",
                            d,
                            mod().file.name(),
                            nd
                    )
            );

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Chaos stage: [red]@[]\nActive class: [magenta]@[]",
                            Chaos.stageIndex(),
                            null //Chaos.stage()
                    )
            );

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Mod info:\n\nName: [gold]@[]\nMinimum version: [blue]@[]\nMod version: [green]@[]\nActive class: [magenta]@[]",
                            Yellow.meta().internalName,
                            Yellow.meta().minGameVersion,
                            Yellow.meta().version,
                            Yellow.mod().main
                    )
            );

            lastFileDate.set(nd.getTime());
        });
    });

    public Yellow(){
        if(Vars.clientLoaded) YellowVars.onImport();

        if(!Vars.clientLoaded) YellowVars.preInit();

        Events.run(EventType.ClientLoadEvent.class, () -> {
            YellowVars.init();
            if(!Vars.mobile && SafeSettings.getBool("yellow-enable-rpc", true, true)) YellowRPC.init();
            YellowSettings.load();
            Rhinor.importMainModPackages(this);
            YellowVars.initNatives();

            if(SafeSettings.get("yellow-check-for-updates", false)) UpdateChecker.loadNotifier();
        });
    }

    public static Mods.ModMeta meta(){
        return Vars.mods.getMod(Yellow.class).meta;
    }

    public static Mods.LoadedMod mod(){
        return Vars.mods.getMod(Yellow.class);
    }

    public static Fi configDir(){
        Fi f = Core.settings.getDataDirectory().child("smol_common").child("yellow");
        f.mkdirs();
        return f;
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();
        YellowWeapons.load();
        YellowUnitTypes.load();
        YellowWeapons.afterLoad();
    }
}
