package yellow;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.type.*;
import yellow.game.*;
import yellow.content.*;
import yellow.internal.*;
import yellow.internal.util.*;
import yellow.type.*;
import yellow.ui.*;
import yellow.ui.buttons.*;
import yellow.ui.buttons.dialogs.*;
import yellow.weapons.*;

import static mindustry.Vars.*;
import static yellow.weapons.YellowWeapons.*;

public class Yellow extends Mod{
    
    public static YellowWeaponSwitch weaponSwitch = new YellowWeaponSwitch();
    public static WeaponInfoDialog weaponInfo;
    public static FoodDialog food;
    public static NotificationListDialog notifs;
    
    public Yellow(){
        String yellow = "yellow suse ballas ";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
            weaponSwitch.build(ui.hudGroup);
            weaponInfo = new WeaponInfoDialog();
            food = new FoodDialog();
            notifs = new NotificationListDialog();

            YellowNotifications.load();

            YellowUtils.mobileHudButton(Icon.add, () -> food.show(Vars.player.team()));

            YellowVars.load();
            
            YellowClassGateway.load();
            //ycg.loadUniversal();

            YellowSettings.load();
            YellowUtils.startRequestLimitHandler();
            
            YellowAutoUpdater.start();
            
            Events.run(Trigger.update, () -> FoodItem.instances.each(FoodItem::update));
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
            YellowPlanets.load();
            YellowBlocks.load();
            YellowItems.load();
            YellowSpells.load();
            YellowTeam.load();

            YellowWeapons.afterLoad();

            YellowUtils.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher, ghostCall, ghostRain, dualSpeedEngine}, true, true, true, YellowUnitTypes.yellow);
        }catch(Exception e){
            Events.run(ClientLoadEvent.class, () -> ui.showCustomConfirm("[red]FATAL LOAD ERROR[]", "An error was thrown while content was being loaded from Yellow.\nClosing the game is heavily recommended.", "Close", "Keep Playing", () -> Core.app.exit(), () -> {}));
        }
    }
}
