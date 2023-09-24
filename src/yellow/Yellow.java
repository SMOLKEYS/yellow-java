package yellow;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.type.*;
import yellow.content.*;
import yellow.internal.*;
import yellow.type.*;
import yellow.ui.*;

import static yellow.content.YellowWeapons.*;

public class Yellow extends Mod{

    public Yellow(){
        //YellowVars.onImport();

        String yellow = "yellow! ";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
            YellowVars.load();
            YellowNotifications.load();
            YellowSettings.load();
            YellowConsoleBind.load();
            YellowAutoUpdater.start();
        });
    }

    @Override
    public void loadContent(){
        YellowBullets.load();
        YellowWeapons.load();
        YellowSpells.load();
        YellowUnitTypes.load();
        YellowWeapons.afterLoad();
        YellowStatusEffects.load();
        YellowBlocks.load();

        DisableableWeapon.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher, ghostCall, ghostRain, dualSpeedEngine, igneous}, true, true, true, YellowUnitTypes.yellow);
    }
}
