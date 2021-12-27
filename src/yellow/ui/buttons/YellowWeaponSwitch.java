package yellow.ui.buttons;

import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import yellow.ui.buttons.dialogs.*;
import mindustry.ui.fragments.*;

import static arc.Core.*;

public class YellowWeaponSwitch extends Fragment{

    private static final float minimapWidth = 150f;

    private YellowWeaponSwitchDialog dialog = new YellowWeaponSwitchDialog();

    public void build(Group parent){
        parent.fill(cont -> {
            cont.name = "weapons switch";
            cont.defaults().size(minimapWidth / 2f);
            cont.marginRight(Scl.scl(minimapWidth));

            Drawable icon = atlas.drawable("status-disarmed");
            cont.button(icon, dialog::show);
        });
    }

}