package yellow.ui.buttons;

import arc.scene.*;
import arc.scene.ui.ImageButton.*;
import arc.scene.style.*;
import yellow.ui.buttons.dialogs.*;
import mindustry.ui.*;
import mindustry.ui.fragments.*;
import mindustry.gen.*;

import static arc.Core.*;

    /**
     * Adds a button anchored to the minimap.
     * @author xzxADIxzx
     */
public class YellowWeaponSwitch extends Fragment{
    /** minimap width, not scaled */
    private static final float width = 150f;
    private static final float padding = 4f;
    private static final float isize = 48f;

    private YellowWeaponSwitchDialog dialog;

    public void build(Group parent){
        dialog = new YellowWeaponSwitchDialog(); // do not create new ones until the client loads
        parent.fill(cont -> {
            cont.name = "weapons switch";
            cont.defaults().size(width / 2f);

            cont.top().right();
            cont.marginRight(width - padding);

            Drawable icon = atlas.drawable("status-disarmed");
            ImageButtonStyle style = new ImageButtonStyle(){{
                up = Tex.pane;
                down = Styles.flatDown;
                over = Styles.flatOver;
            }};
            
            cont.button(icon, style, isize, dialog::show);
        });
    }

}