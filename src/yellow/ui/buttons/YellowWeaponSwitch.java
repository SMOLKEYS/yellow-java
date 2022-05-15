package yellow.ui.buttons;

import arc.flabel.*;
import arc.scene.*;
import arc.scene.ui.ImageButton.*;
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import yellow.ui.buttons.dialogs.*;
import yellow.content.*;
import mindustry.ui.*;
import mindustry.gen.*;

import static arc.Core.*;
import static mindustry.Vars.*;

    /**
     * Adds a button anchored to the minimap
     * @author xzxADIxzx
     */
public class YellowWeaponSwitch{
    /** minimap width, not scaled */
    private static final float width = 150f;
    private static final float padding = 4f;
    private static final float isize = 48f;

    private YellowWeaponSwitchDialog dialog;

    public void build(Group parent){
        Drawable icon = atlas.drawable("status-disarmed");
        ImageButtonStyle style = new ImageButtonStyle(){{
            up = Tex.pane;
            down = Styles.flatDown;
            over = Styles.flatOver;
        }};

        dialog = new YellowWeaponSwitchDialog(); // do not create new ones until the client loads
        parent.fill(cont -> {
            cont.name = "weapons switch";
            cont.defaults().size(width / 2f);

            cont.top().right();
            cont.marginRight(width - padding);
            
            cont.button(icon, style, isize, dialog::show);

            // show buttons only when player controls yellow air
            cont.visible(() -> player.unit().type == YellowUnitTypes.yellow);
        });
    }

}
