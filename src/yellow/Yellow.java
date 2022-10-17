package yellow;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mod;
import mindustry.type.Weapon;
import yellow.content.*;
import yellow.internal.YellowClassGateway;
import yellow.internal.util.YellowUtils;
import yellow.ui.YellowSettings;
import yellow.ui.buttons.YellowWeaponSwitch;
import yellow.weapons.YellowWeapons;

import static mindustry.Vars.ui;
import static yellow.weapons.YellowWeapons.*;

public class Yellow extends Mod{
    
    public static YellowWeaponSwitch weaponSwitch = new YellowWeaponSwitch();
    
    public Yellow(){
        String yellow = "yellow suse ";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
            weaponSwitch.build(ui.hudGroup);
            
            YellowClassGateway ycg = new YellowClassGateway();
            ycg.load();
            ycg.loadUniversal();

            YellowSettings.INSTANCE.load();
            YellowUtils.startRequestLimitHandler();
        });
    }
    
    @Override
    public void loadContent(){
        YellowBullets.load();
        YellowWeapons.load();

        YellowUnitTypes.load();
        YellowStatusEffects.load();
        YellowPlanets.load();
        YellowBlocks.load();

        YellowWeapons.afterLoad();

        YellowUtils.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher, ghostCall}, true, true, YellowUnitTypes.yellow);
    }
    
}
