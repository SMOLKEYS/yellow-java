package yellow;

import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.mod.*;
import yellow.content.*;
import yellow.core.*;
import yellow.game.*;
import yellow.internal.*;
import yellow.ui.*;
import yellow.util.*;

import java.util.*;

public class YellowVars{

    public static YellowUI ui;

    public static void load(){
        Time.mark();

        if(!Vars.headless){
            ui = new YellowUI();
            ui.init();

            YellowNotifications.load();
            YellowSettings.load();
            YellowAutoUpdater.start();
            Time.run(120f, YellowConsoleBind::load);

            YellowAchievements.installed.unlock();
            YState.INSTANCE.load();
        }

        Mods.ModMeta meta = getSelf().meta;
        MetaChaos.load();
        YellowUtils.loop(1f, () -> MetaChaos.update(meta));

        Log.info("Loaded all of Yellow in @ seconds", Time.elapsed());
    }

    public static Mods.LoadedMod getSelf(){
        return Vars.mods.getMod("yellow-java");
    }

    public static boolean isCustomClient(){
        return Objects.equals(Version.type, "custom");
    }
}
