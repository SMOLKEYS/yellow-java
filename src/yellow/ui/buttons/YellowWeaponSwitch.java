package yellow.ui.buttons;

import arc.scene.*;
import arc.scene.ui.ImageButton.*;
import arc.scene.style.*;
import yellow.ui.buttons.dialogs.*;
import mindustry.ui.*;
import mindustry.ui.fragments.*;
import mindustry.gen.*;

import static arc.Core.*;

public class YellowWeaponSwitch extends Fragment{

    private static final float minimapWidth = 150f;

    private YellowWeaponSwitchDialog dialog;

    public void build(Group parent){
        dialog = new YellowWeaponSwitchDialog(); // do not create new ones until the client loads
        parent.fill(cont -> {
            cont.name = "weapons switch";
            cont.defaults().size(minimapWidth / 2f);

            cont.top().right();
            cont.marginRight(minimapWidth);

            Drawable icon = atlas.drawable("status-disarmed");
            ImageButtonStyle style = new ImageButtonStyle(){{
                up = Tex.pane;
                down = Styles.flatDown;
                over = Styles.flatOver;
            }};

            cont.button(icon, style, dialog::show).row();
            cont.button(icon, style, dialog::show);
        });
    }

}