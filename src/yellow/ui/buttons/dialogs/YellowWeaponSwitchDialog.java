package yellow.buttons.dialogs;

import arc.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.Button.*;
import arc.scene.ui.CheckBox.*;
import arc.scene.ui.layout.*;
import arc.scene.ui.layout.Table.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.dialogs.BaseDialog.*;
import yellow.content.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class YellowWeaponSwitchDialog extends BaseDialog{
    
    public YellowWeaponSwitchDialog(){
        super("@yellow-weapon-switch.name");
        
        cont.add("Weapon Switch");
        addCloseButton();
        
        for(int i = 0; i < YellowUnitTypes.yellowAir.weapons.size; i++){
            cont.row();
            cont.add(new CheckBox(YellowUnitTypes.yellowAir.weapons.get(i).name));
        };
    }
}