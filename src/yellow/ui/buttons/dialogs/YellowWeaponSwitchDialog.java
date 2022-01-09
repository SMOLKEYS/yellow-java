package yellow.ui.buttons.dialogs;

import arc.util.*;
import arc.util.Log.*;
import arc.scene.ui.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import yellow.content.*;
import yellow.weapons.*;

public class YellowWeaponSwitchDialog extends BaseDialog{
    
    public YellowWeaponSwitchDialog(){
        super("Weapon Switch");
        
        cont.add("Weapon Switch").row();
        addCloseButton();
        
        for(int i = 0; i < YellowUnitTypes.yellowAir.weapons.size; i++){
            final int id = i;
            
            cont.check(YellowUnitTypes.yellowAir.weapons.get(id).name, true, it -> {
                //some sussy stuff 	
            }).update(check -> {
            	Unit unit = Vars.player.unit(); // !!! PUT SOMETHING ELSE HERE !!!
            	if (!check.isChecked()) unit.mounts[id].reload = 60f;
            }).row();
        };
    };
}
