package yellow;

import arc.*;
import arc.files.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import yellow.core.YellowEventType.*;
import yellow.content.*;
import yellow.entities.*;
import yellow.gen.*;
import yellow.js.*;
import yellow.spec.*;
import yellow.ui.*;
import yellow.util.*;
import yellow.util.variable.SettingBoundVariable.*;

import java.util.*;
import java.util.concurrent.atomic.*;

import static yellow.YellowSettingValues.*;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class Yellow extends Mod{

    static final LongSetting lastFileDate = new LongSetting("yellow-debug-lastfiledate", 1997L);

    public static final boolean debug = YellowJVM.hasParameter("yellow-debug", () -> Objects.equals(System.getenv("YELLOW_HAKAMAKADAJAKA"), "fumo"), str -> {
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

        if(!Vars.clientLoaded){
            YellowGroups.init();
            YellowVars.preInit();
        }

        Events.run(ClientLoadEvent.class, () -> {
            YellowVars.init();
            YellowSettings.load();
            YellowFonts.load();
            YellowStyles.load();
            YellowVars.initNatives();
            Rhinor.importMainModPackages(this);

            if(!Vars.mobile && enableRpc.get()) YellowRPC.init();

            if(enableAutoupdate.get()) UpdateChecker.loadNotifier();
        });
    }

    public static ModMeta meta(){
        return Vars.mods.getMod(Yellow.class).meta;
    }

    public static LoadedMod mod(){
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
