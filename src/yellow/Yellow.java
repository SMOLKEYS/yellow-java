package yellow;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import yellow.internal.*;

public class Yellow extends Mod{

    public static boolean cheats = false, debug = false;

    public Yellow(){
        //YellowVars.onImport();

        var yellow = "yellow time! ";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, YellowVars::load);
    }

    @Override
    public void loadContent(){
        Time.mark();
        YellowContentLoader.load();
        Log.info("Loaded all Yellow content and special disableable weapon mirror in @ seconds", Time.elapsed());
    }
}
