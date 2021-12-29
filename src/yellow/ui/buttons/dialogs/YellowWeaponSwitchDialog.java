package yellow.ui.buttons.dialogs;

import arc.scene.ui.*;
import mindustry.ui.dialogs.*;
import yellow.content.*;
import yellow.weapons.*;

public class YellowWeaponSwitchDialog extends BaseDialog{
    
    public YellowWeaponSwitchDialog(){
        super("Weapon Switch");
        
        cont.add("Weapon Switch");
        addCloseButton();
        
        for(int i = 0; i < YellowUnitTypes.yellowAir.weapons.size; i++){
            cont.row();
            cont.add(new CheckBox(YellowWeapons.get(i)));
        };
    }
}