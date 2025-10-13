package yellow;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.game.*;
import mindustry.mod.*;

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

        FRegistry.load();

        Mods.ModMeta meta = getSelf().meta;
        MetaChaos.load();
        YellowUtils.loop(1f, () -> MetaChaos.update(meta));

        Events.run(EventType.Trigger.update, YellowUpdateCore::updateNoncontent);

        Log.info("Loaded all of Yellow in @ seconds", Time.elapsed());
    }

    public static Mods.LoadedMod getSelf(){
        return Vars.mods.getMod("yellow-java");
    }

    public static boolean isCustomClient(){
        return Objects.equals(Version.type, "custom");
    }
}
