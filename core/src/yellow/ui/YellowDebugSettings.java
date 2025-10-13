package yellow.ui;

import arc.*;
import mindustry.*;
import mindustry.ui.dialogs.*;
import yellow.*;
import yellow.util.*;

import static yellow.ui.YellowSettings.*;

class YellowDebugSettings{

    static void build(SettingsMenuDialog.SettingsTable t){
        t.textPref("yellow-crash-text", "manually initiated crash");

        buttonPref(t, "yellow-crash-manual", () -> {
            throw new RuntimeException(SafeSettings.getString("yellow-crash-text", "manually initiated crash", "manually initiated crash"));
        });

        buttonPref(t, "yellow-tooltip-everything", () -> {
            YellowVars.notifrag.showNotification("why?");
            Core.scene.root.forEach(el -> Vars.ui.addDescTooltip(el, "uhm excuse me what the actual fuck are you doing in my house"));
        });
    }
}
