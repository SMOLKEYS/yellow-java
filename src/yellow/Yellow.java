package yellow;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mod;
import mindustry.type.Weapon;
import yellow.content.*;
import yellow.ctype.FallbackContentList;
import yellow.internal.YellowClassGateway;
import yellow.internal.util.YellowUtils;
import yellow.ui.YellowSettings;
import yellow.ui.buttons.YellowWeaponSwitch;
import yellow.weapons.YellowWeapons;

import static arc.Core.app;
import static mindustry.Vars.headless;
import static mindustry.Vars.ui;
import static yellow.weapons.YellowWeapons.*;

public class Yellow extends Mod{
    
    public static YellowWeaponSwitch weaponSwitch = new YellowWeaponSwitch();
    
    public Yellow(){
        String yellow = "yellow";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
            weaponSwitch.build(ui.hudGroup);
            
            YellowClassGateway ycg = new YellowClassGateway();
            ycg.load();

            YellowSettings.INSTANCE.load();
        });
        
        if(headless){
            Log.err("Yellow-chan: I'm sorry, but the mod I'm on (Yellow (Java)) does not work in multiplayer due to using singleplayer-only variables and desync issues. Also, here's a dev note:");
            Log.err("I'm not making Yellow (Java) multiplayer-compatible. That seems like too much effort and most of the mod's upcoming/current content was never meant for multiplayer anyways. Terminating server...");
            app.exit();
        }
    }

    public final FallbackContentList[] yellowContent = {
        new YellowUnitTypes(),
        new YellowStatusEffects(),
        new YellowPlanets(),
        new YellowBlocks()
    };
    
    public final FallbackContentList bullets = new YellowBullets();
    
    @Override
    public void loadContent(){
        bullets.load();
        YellowWeapons.init();
        
        for(FallbackContentList list : yellowContent){
            list.load();
        }

        YellowUtils.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher}, true, true, YellowUnitTypes.yellow);
    }
    
}
