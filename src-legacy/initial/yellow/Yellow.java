package yellow;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;

public class Yellow extends Mod{

    public static boolean cheats = false, debug = false;

    public Yellow(){
        if(YellowPermVars.INSTANCE.getDisabled()) return;
        Events.run(ClientLoadEvent.class, () -> Vars.ui.showCustomConfirm(
                "Yellow: Rewritten",
                "@yellow.finalver",
                "Disable",
                "Ignore",
                () -> {
                    YellowPermVars.INSTANCE.setDisabled(true);
                    Vars.ui.showCustomConfirm(
                            "Finished",
                            "[red]This version of Yellow has been permanently disabled.[]\n\nRefer to the buttons below to see why Yellow is being rewritten.",
                            "README.md",
                            "Rewritten",
                            () -> Core.app.openURI("https://github.com/SMOLKEYS/yellow-java"),
                            () -> Core.app.openURI("https://github.com/SMOLKEYS/yellow-rewritten")
                    );
                },
                () -> {}
        ));

        var yellow = "yellow time! ";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, YellowVars::load);
    }

    @Override
    public void loadContent(){
        if(YellowPermVars.INSTANCE.getDisabled()) return;
        Time.mark();
        YellowContentLoader.load();
        Log.info("Loaded all Yellow content and special disableable weapon mirror in @ seconds", Time.elapsed());
    }
}
