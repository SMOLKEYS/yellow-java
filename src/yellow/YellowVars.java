package yellow;

import arc.*;
import mindustry.mod.*;
import yellow.internal.*;
import yellow.internal.util.*;
import yellow.ui.buttons.*;
import yellow.ui.buttons.dialogs.*;

import static mindustry.Vars.*;

public class YellowVars{

    public static YellowControl yellowControl = new YellowControl();
    public static WeaponInfoDialog weaponInfo;
    public static NotificationListDialog notifs;

    public static void load(){
        yellowControl.build(ui.hudGroup);
        weaponInfo = new WeaponInfoDialog();
        notifs = new NotificationListDialog();

        YellowUtils.emptyHudButtonRow();

        Mods.ModMeta meta = Yellow.getSelf().meta;

        MetaChaos.load();

        meta.subtitle = MetaChaos.getSubtitles().random();
        meta.description = MetaChaos.getDescriptions().random();

        YellowUtils.loop(1f, () -> {
            meta.subtitle = MetaChaos.getSubtitles().random();
            meta.description = MetaChaos.getDescriptions().random();
        });


        if(YellowPermVars.INSTANCE.getTemporary()){
            Core.app.addListener(new ApplicationListener(){
                @Override
                public void exit(){
                    Yellow.getSelf().file.delete();
                }
            });
        }
    }
}
