package yellow.ui.buttons;

import arc.scene.*;
import arc.scene.style.*;
import yellow.content.*;
import yellow.internal.util.*;
import yellow.ui.buttons.dialogs.*;

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

        dialog = new YellowWeaponSwitchDialog(); // do not create new ones until the client loads

        if(mobile){
            YellowUtils.mobileHudButton(icon, () -> {
                if(player.unit().type == YellowUnitTypes.yellow) dialog.show(player.unit().mounts);
            });
        }else{
            ImageButtonStyle style = new ImageButtonStyle(){{
                up = Tex.pane;
                down = Styles.flatDown;
                over = Styles.flatOver;
            }};
            
            parent.fill(cont -> {
                cont.name = "weapons switch";
                cont.defaults().size(width / 2f);
    
                cont.top().right();
                cont.marginRight(width - padding);
    
                cont.button(icon, style, isize, () -> dialog.show(player.unit().mounts));
    
                // show buttons only when player controls yellow air
                cont.visible(() -> player.unit().type == YellowUnitTypes.yellow);
        }
    }
}
