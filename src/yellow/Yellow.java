package yellow;

import arc.Events;
import arc.scene.style.Drawable;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Icon;
import mindustry.mod.Mod;
import mindustry.mod.Mods;
import mindustry.type.Weapon;
import yellow.content.*;
import yellow.internal.YellowClassGateway;
import yellow.internal.util.YellowUtils;
import yellow.ui.YellowSettings;
import yellow.ui.buttons.YellowWeaponSwitch;
import yellow.ui.buttons.dialogs.FoodDialog;
import yellow.weapons.YellowWeapons;

import static mindustry.Vars.ui;
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
            if(Vars.mobile){
                try{
                    YellowUtils.mobileHudButton(Icon.defense, () -> food.show(Vars.player.team()));
                }catch(Exception e){
                    //nothing
                }
            }

            YellowVars.load();
            
            YellowClassGateway ycg = new YellowClassGateway();
            ycg.load();
            //ycg.loadUniversal();

            YellowSettings.INSTANCE.load();
            YellowUtils.startRequestLimitHandler();
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

        YellowUtils.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher, ghostCall}, true, true, true, YellowUnitTypes.yellow);
    }
}
