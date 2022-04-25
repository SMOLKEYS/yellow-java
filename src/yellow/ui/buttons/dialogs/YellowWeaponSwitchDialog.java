package yellow.ui.buttons.dialogs;

import arc.util.*;
import arc.util.Log.*;
import arc.scene.ui.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import yellow.type.*;
import yellow.content.*;
import yellow.weapons.*;
import yellow.entities.units.*;

public class YellowWeaponSwitchDialog extends BaseDialog{
    
    public YellowWeaponSwitchDialog(){
        super("Weapon Switch");
        
        cont.add("Weapon Switch").row();
        addCloseButton();
        
        for(int i = 0; i < YellowUnitTypes.yellow.weapons.size; i++){
            final int id = i;
            
            cont.check(((NameableWeapon) YellowUnitTypes.yellow.weapons.get(id)).displayName + " Lock", false, it -> {
                Unit unit = Vars.player.unit();
                var m = ((DisableableWeaponMount) unit.mounts[id]);
                m.disabled = it;
            }).row();
        };
    };
}
