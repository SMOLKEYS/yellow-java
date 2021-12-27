package yellow;

import io.mnemotechnician.autoupdater.*;
import arc.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import yellow.content.*;
import yellow.weapons.*;
import yellow.ui.buttons.*;

public class Yellow extends Mod{
    
    public static YellowWeaponSwitch weaponSwitch;
    
    public Yellow(){
        String yellow = "yellow";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
        	Updater.checkUpdates(this);

            weaponSwitch = new YellowWeaponSwitch();
        });
    };
    
    public final ContentList[] yellowContent = {
        new YellowUnitTypes(),
        new YellowStatusEffects(),
        new YellowPlanets()
    };
    
    @Override
    public void loadContent(){
        YellowWeapons.init();
        
        for(ContentList list : yellowContent){
            list.load();
        };
    }
    
}