package yellow;

import arc.*;
import arc.util.*;
import mindustry.type.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import yellow.internal.util.*;
import yellow.ctype.*;
import yellow.world.blocks.units.SummoningShrine.SummoningShrineBuild;
import yellow.content.*;
import yellow.weapons.*;
import yellow.ui.buttons.*;
import yellow.internal.*;
import yellow.world.*;

import static mindustry.Vars.*;
import static arc.Core.*;
import static yellow.weapons.YellowWeapons.*;

public class Yellow extends Mod{
    
    public static YellowWeaponSwitch weaponSwitch = new YellowWeaponSwitch();
    
    public Yellow(){
        String yellow = "yellow";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
            weaponSwitch.build(ui.hudGroup);
            
            YellowClassGateway ycg = new YellowClass
            ();
            ycg.load();
        });
        
        if(headless){
            Log.err("Yellow-chan: I'm sorry, but the mod I'm on (Yellow (Java)) does not work in multiplayer due to using singleplayer-only variables and desync issues. Also, here's a dev note:");
            Log.err("I'm not making Yellow (Java) multiplayer-compatible. That seems like too much effort and most of the mod's upcoming/current content was never mean't for multiplayer anyways. Terminating server...");
            app.exit();
        }
    };
    
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
        };
        
        YellowUtils.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher}, true, true, YellowUnitTypes.yellow);
    }
    
}
