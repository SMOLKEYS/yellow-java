package yellow;

import arc.discord.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.fragments.*;
import yellow.content.*;
import yellow.game.*;
import yellow.internal.*;
import yellow.util.*;
import yellow.ui.*;
import yellow.ui.buttons.*;
import yellow.ui.buttons.dialogs.*;

import java.util.*;

import static mindustry.Vars.*;

public class YellowVars{

    public static YellowControl yellowControl;
    public static WeaponInfoDialog weaponInfo;
    public static NotificationListDialog notifs;
    public static AchievementListDialog achievements;
    public static BullethellSessionManagerDialog bullethellSessionManager;

    public static void load(){
        Time.mark();

        yellowControl = new YellowControl();
        weaponInfo = new WeaponInfoDialog();
        notifs = new NotificationListDialog();
        achievements = new AchievementListDialog();
        bullethellSessionManager = new BullethellSessionManagerDialog();
        yellowControl.build(ui.hudGroup);

        YellowNotifications.load();
        YellowSettings.load();
        YellowAutoUpdater.start();
        YellowConsoleBind.load();
        YellowAchievements.installed.unlock();
        YState.INSTANCE.load();
        Time.run(120f, YellowConsoleBind::load);

        Mods.ModMeta meta = getSelf().meta;
        MetaChaos.load();
        YellowUtils.loop(1f, () -> MetaChaos.update(meta));

        var buttons = new MenuFragment.MenuButton[]{
                new MenuFragment.MenuButton("@menu.achievements", Icon.tree, achievements::show),
                new MenuFragment.MenuButton("@menu.notifications", Icon.warning, () -> notifs.show(Notification.Companion.getInstances()))
        };

        if(!mobile){
            ui.menufrag.addButton(new MenuFragment.MenuButton("@menu.yellow", Icon.right, null, buttons));

            Log.info("Yellow RPC backend ready for use.");
            if(YellowPermVars.INSTANCE.getEnableRpc()){
                try{
                    //yellow's rpc dependency is seperate from the one in mindus
                    //TODO do not use rhino for doing this, also occasionally does not work, fuck.
                    Vars.mods.getScripts().runConsole("Packages.arc.discord.DiscordRPC.close()");
                    RichPresenceChaos.load();
                    DiscordRPC.connect(1204425960037027900L);
                    YellowRPC.send();
                }catch(Exception e){
                    Log.err(e);
                }
            }

            YellowUtils.loop(30, () -> {
                if(YellowPermVars.INSTANCE.getEnableRpc()) YellowRPC.send();
            });

        }else{
            for(MenuFragment.MenuButton b: buttons){
                ui.menufrag.addButton(b);
            }

        }

        Log.info("Loaded all of Yellow in @ seconds", Time.elapsed());
    }

    public static Mods.LoadedMod getSelf(){
        return Vars.mods.getMod("yellow-java");
    }

    public static boolean isCustomClient(){
        return Objects.equals(Version.type, "custom");
    }
}
