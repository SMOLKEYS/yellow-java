package yellow.ui.buttons;

import arc.scene.Group;
import arc.scene.style.Drawable;
import arc.scene.ui.ImageButton.ImageButtonStyle;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import yellow.content.YellowUnitTypes;
import yellow.internal.util.YellowUtils;
import yellow.ui.buttons.dialogs.YellowWeaponSwitchDialog;

import static arc.Core.atlas;
import static mindustry.Vars.player;

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

    public void build(){
        Drawable icon = atlas.drawable("status-disarmed");

        dialog = new YellowWeaponSwitchDialog(); // do not create new ones until the client loads

        YellowUtils.mobileHudButton(icon, () -> {
            if(player.unit().type == YellowUnitTypes.yellow) dialog.show(player.unit().mounts);
        });
    }

}
