package yellow;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.type.*;
import yellow.type.*;
import yellow.content.*;
import yellow.internal.*;
import yellow.internal.util.*;
import yellow.ui.*;
import yellow.ui.buttons.*;
import yellow.ui.buttons.dialogs.*;
import yellow.weapons.*;

import static mindustry.Vars.*;
import static yellow.weapons.YellowWeapons.*;

public class Yellow extends Mod{
    
    public static YellowWeaponSwitch weaponSwitch = new YellowWeaponSwitch();
    public static FoodDialog food;
    
    public Yellow(){
        String yellow = "yellow suse ";
        for(int i = 0; i < 5; i++) yellow += yellow;
        Log.info(yellow);
        
        Events.run(ClientLoadEvent.class, () -> {
            weaponSwitch.build(ui.hudGroup);
            food = new FoodDialog();

            YellowUtils.mobileHudButton(Icon.add, () -> food.show(Vars.player.team()));

            YellowVars.load();
            
            YellowClassGateway ycg = new YellowClassGateway();
            ycg.load();
            //ycg.loadUniversal();

            YellowSettings.INSTANCE.load();
            YellowUtils.startRequestLimitHandler();
            
            BleedingEdgeAutoUpdater.INSTANCE.start();
            
            Events.run(Trigger.update, () -> FoodItem.instances.each(itm -> itm.update()));
        });
        
    }

    public static Mods.LoadedMod getSelf(){
        return Vars.mods.getMod("yellow-java");
    }
    
    @Override
    public void loadContent(){
        YellowBullets.load();
        YellowWeapons.load();

        YellowUnitTypes.load();
        YellowStatusEffects.load();
        YellowPlanets.load();
        YellowBlocks.load();
        YellowItems.load();

        YellowWeapons.afterLoad();

        YellowUtils.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher, ghostCall, ghostRain}, true, true, true, YellowUnitTypes.yellow);
    }
}
