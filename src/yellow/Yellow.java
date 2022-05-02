package yellow;

import io.mnemotechnician.autoupdater.*;
import arc.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import yellow.world.blocks.units.SummoningShrine.SummoningShrineBuild;
import yellow.content.*;
import yellow.weapons.*;
import yellow.ui.buttons.*;
import yellow.world.*;
import yellow.interactions.*;
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
            Time.runTask(120f, () -> {
                DialogueBox.dialogueStart(Responses.test);
            });
            
            app.post(() -> {
                int amogus = 20;
            });
        });
        
        if(headless){
            Log.err("Yellow-chan: I'm sorry, but the mod I'm on (Yellow (Java)) does not work in multiplayer due to using singleplayer-only variables and desync issues. Also, here's a dev note:");
            Log.err("I'm not making Yellow (Java) multiplayer-compatible. That seems like too much effort and most of the mod's upcoming/current content was never mean't for multiplayer anyways. Terminating server...");
            app.exit();
        }
    };
    
    public final ContentList[] yellowContent = {
        new YellowUnitTypes(),
        new YellowStatusEffects(),
        new YellowPlanets(),
        new YellowBlocks()
    };
    
    public final ContentList bullets = new YellowBullets();
    
    @Override
    public void loadContent(){
        bullets.load();
        YellowWeapons.init();
        WorldVars.prepare();
        WorldVars.start();
        
        for(ContentList list : yellowContent){
            list.load();
        };
    }
    
}
