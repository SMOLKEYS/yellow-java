package yellow;

import arc.*;
import arc.files.*;
import mindustry.game.*;
import mindustry.mod.*;

//third rewrite note: the more i do this the more i wanna throw my laptop against a wall
@SuppressWarnings({
        "unused", //oh and shut the fuck up java
        "GrazieInspection" //you too intellij
})
public class Yellow extends Mod{

    public Yellow(){
        Events.run(EventType.ClientLoadEvent.class, () -> {
            YellowVars.initUI();

            UpdateChecker.loadNotifier();
            JSLink.importPackage(
                    "yellow", "yellow.ui", "yellow.ui.fragments", "yellow.world.meta",
                    "yellow.ai", "yellow.content", "yellow.entities.units", "yellow.entities.effect",
                    "yellow.entities.units.entity", "yellow.io", "yellow.math", "yellow.type.weapons"
            );
        });
    }

    public static Fi configDir(){
        Fi f = Core.settings.getDataDirectory().child("smol_common").child("yellow");
        f.mkdirs();
        return f;
    }

}
