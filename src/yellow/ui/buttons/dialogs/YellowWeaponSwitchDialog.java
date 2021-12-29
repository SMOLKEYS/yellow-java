package yellow.ui.buttons.dialogs;

import arc.util.*;
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
            var weapon = new CheckBox(YellowUnitTypes.yellowAir.weapons.get(i).name);
            
            cont.row();
            cont.add(weapon);
            
            weapon.update(() -> {
                if(weapon.isChecked() == true){
                    print("no");
                }
            });
            
            };
        };
    }