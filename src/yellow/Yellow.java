package yellow;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.type.*;
import yellow.content.*;
import yellow.internal.*;
import yellow.internal.util.*;
import yellow.ui.*;
import yellow.ui.buttons.*;
import yellow.ui.buttons.dialogs.*;
import yellow.weapons.*;

import static mindustry.Vars.*;
import static yellow.weapons.YellowWeapons.*;

public class Yellow extends Mod{
    
    public static YellowWeaponSwitch weaponSwitch = new YellowWeaponSwitch();
    public static WeaponInfoDialog weaponInfo;
    public static NotificationListDialog notifs;
    
    public Yellow(){
        String yellow = "yellow! ";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
            weaponSwitch.build(ui.hudGroup);
            weaponInfo = new WeaponInfoDialog();
            notifs = new NotificationListDialog();

            YellowNotifications.load();

            YellowUtils.emptyHudButtonRow();
            
            YellowVars.load();

            YellowSettings.load();
            
            YellowAutoUpdater.start();
        });
        
    }

    public static Mods.LoadedMod getSelf(){
        return Vars.mods.getMod("yellow-java");
    }
    
    @Override
    public void loadContent(){
        try{
            YellowBullets.load();
            YellowWeapons.load();
            YellowUnitTypes.load();
            YellowStatusEffects.load();
            YellowBlocks.load();

            YellowWeapons.afterLoad();

            YellowUtils.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher, ghostCall, ghostRain, dualSpeedEngine}, true, true, true, YellowUnitTypes.yellow);
        }catch(Exception e){
            Events.run(ClientLoadEvent.class, () -> ui.showCustomConfirm("[red]FATAL LOAD ERROR[]", "An error was thrown while content was being loaded from Yellow.\nClosing the game is heavily recommended.", "Close", "Keep Playing", () -> Core.app.exit(), () -> {}));
        }
    }
}
