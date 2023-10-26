package yellow;

import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.fragments.*;
import yellow.content.*;
import yellow.game.*;
import yellow.internal.*;
import yellow.internal.util.*;
import yellow.ui.*;
import yellow.ui.buttons.*;
import yellow.ui.buttons.dialogs.*;

import static mindustry.Vars.*;

public class YellowVars{


    public static boolean debugMode = false;

    public static YellowControl yellowControl;
    public static WeaponInfoDialog weaponInfo;
    public static NotificationListDialog notifs;
    public static AchievementListDialog achievements;

    public static void load(){
        yellowControl = new YellowControl();
        weaponInfo = new WeaponInfoDialog();
        notifs = new NotificationListDialog();
        achievements = new AchievementListDialog();
        yellowControl.build(ui.hudGroup);

        YellowNotifications.load();
        YellowSettings.load();
        YellowAutoUpdater.start();
        YellowAchievements.installed.unlock();
        YState.INSTANCE.load();
        Time.run(120f, YellowConsoleBind::load);

        Mods.ModMeta meta = getSelf().meta;
        MetaChaos.load();
        YellowUtils.loop(1f, () -> MetaChaos.update(meta));

        MenuFragment.MenuButton[] buttons = {
                new MenuFragment.MenuButton("Achievements", Icon.tree, achievements::show),
                new MenuFragment.MenuButton("Notifications", Icon.warning, notifs::show)
        };

        if(!mobile){
            ui.menufrag.addButton(new MenuFragment.MenuButton("Yellow", Icon.right, null, buttons));
        }else{
            for(MenuFragment.MenuButton b: buttons){
                ui.menufrag.addButton(b);
            }
        }
    }

    public static Mods.LoadedMod getSelf(){
        return Vars.mods.getMod("yellow-java");
    }
}
