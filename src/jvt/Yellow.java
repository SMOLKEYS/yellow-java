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

public class Yellow extends Mod{
    
    public Yellow(){
        Log.info("amogus");
        });
    };
    
    private final ContentList[] yellowContent = {
        new YellowUnitTypes()
    };
    
    @Override
    public void loadContent(){
        for(ContentList list : jvtContent){
            list.load();
        };
    }
    
}