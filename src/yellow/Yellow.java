package yellow;

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

public class Yellow extends Mod{
    
    public Yellow(){
        Log.info("amogus");
    };
    
    private final ContentList[] yellowContent = {
        new YellowFx(),
        new YellowUnitTypes()
    };
    
    @Override
    public void loadContent(){
        YellowWeapons.init();
        
        for(ContentList list : yellowContent){
            list.load();
        };
    }
    
}