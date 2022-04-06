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
import yellow.world.*;
import yellow.interactions.ui.*;
import yellow.interactions.ui.DialogueBox.*;

import static mindustry.Vars.*;
import static arc.Core.*;

public class Yellow extends Mod{
    
    public static YellowWeaponSwitch weaponSwitch = new YellowWeaponSwitch();
    
    public Yellow(){
        String yellow = "yellow";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
            Updater.checkUpdates(this);
            
            weaponSwitch.build(ui.hudGroup);
            
            settings.put("current-session-menu-time", 0);
            
            Time.runTask(60f, () -> {
                DialogueBox.build();
            });
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
        WorldVars.prepare();
        WorldVars.start();
        
        for(ContentList list : yellowContent){
            list.load();
        };
    }
    
}
