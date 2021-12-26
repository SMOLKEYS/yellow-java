package yellow;

import io.mnemotechnician.autoupdater.*;
import arc.*;
import arc.util.*;
import arc.util.Log.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import mindustry.ui.dialogs.*;
import yellow.content.*;
import yellow.weapons.*;
import yellow.dialogs.*;

public class Yellow extends Mod{
    
    
    public Yellow(){
        String yellow = "yellow";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
        	Updater.checkUpdates(this);
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