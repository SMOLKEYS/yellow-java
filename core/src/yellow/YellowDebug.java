package yellow;

import arc.*;
import arc.util.*;
import mindustry.gen.*;
import yellow.core.YellowEventType.*;
import yellow.spec.*;
import yellow.util.variable.SettingBoundVariable.*;

import java.util.*;

import static yellow.Yellow.*;

public class YellowDebug{

    static final LongSetting lastFileDate = new LongSetting("yellow-debug-lastfiledate", 1997L);

    static void loadDebug(){
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
                            mod().main
                    )
            );

            lastFileDate.set(nd.getTime());
        });
    }
}
