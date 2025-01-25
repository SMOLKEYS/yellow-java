package yellow.core;

import arc.discord.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.fragments.*;
import yellow.*;
import yellow.game.*;
import yellow.internal.*;
import yellow.ui.dialogs.*;
import yellow.ui.fragments.*;
import yellow.util.*;

import static mindustry.Vars.*;

public class YellowUI{
    public YellowControlFragment controlfrag;
    public YellowDebugFragment debugfrag;
    public YellowDialogueBoxFragment boxfrag;

    public YellowControlDialog yellowControl;
    public NotificationListDialog notifs;
    public AchievementListDialog achievements;

    public void init(){
        controlfrag = new YellowControlFragment();
        debugfrag = new YellowDebugFragment();
        boxfrag = new YellowDialogueBoxFragment();

        yellowControl = new YellowControlDialog();
        notifs = new NotificationListDialog();
        achievements = new AchievementListDialog();

        controlfrag.build(ui.hudGroup);
        debugfrag.build(ui.hudGroup);
        boxfrag.build(ui.hudGroup);

        var buttons = new MenuFragment.MenuButton[]{
                new MenuFragment.MenuButton("@menu.achievements", Icon.tree, achievements::show),
                new MenuFragment.MenuButton("@menu.notifications", Icon.warning, () -> notifs.show(Notification.Companion.getInstances()))
        };

        if(!mobile){
            Vars.ui.menufrag.addButton(new MenuFragment.MenuButton("@menu.yellow", Icon.right, null, buttons));

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
                Vars.ui.menufrag.addButton(b);
            }

        }
    }
}
