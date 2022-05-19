package yellow.ui.buttons.dialogs;

import arc.util.*;
import arc.util.Log.*;
import arc.scene.ui.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import mindustry.entities.units.*;
import yellow.type.*;
import yellow.content.*;
import yellow.weapons.*;
import yellow.entities.units.*;
import yellow.interactions.*;
import yellow.interactions.ui.*;

import static yellow.interactions.Responses.*;

public class YellowWeaponSwitchDialog extends BaseDialog{
    private static DialogueBox box = new DialogueBox();
    
    public YellowWeaponSwitchDialog(){
        super("Weapon Switch");
        
        cont.add("Weapon Switch").row();
        addCloseButton();
        
        for(int i = 0; i < YellowUnitTypes.yellow.weapons.size; i++){
            final int id = i;
            
            cont.check(((NameableWeapon) YellowUnitTypes.yellow.weapons.get(id)).displayName + " Lock", false, it -> {
                Unit unit = Vars.player.unit();
                DisableableWeaponMount m = ((DisableableWeaponMount) unit.mounts[id]);
                m.disabled = it;
            }).row();
        };
        
        cont.button("Request Leave", () -> {
            box.dialogueStart(leaveResponse, new Runnable[]{
                () -> Log.info("To be tested...")
            }, new int[]{2});
        }).row();
    };
}
