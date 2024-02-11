package yellow.ui.buttons;

import arc.scene.*;
import arc.scene.style.*;
import arc.scene.ui.ImageButton.*;
import mindustry.gen.*;
import mindustry.ui.*;
import yellow.entities.units.entity.*;
import yellow.util.*;
import yellow.ui.buttons.dialogs.*;

import static arc.Core.*;
import static mindustry.Vars.*;

    /**
     * Adds a button anchored to the minimap, or a new row of buttons on mobile.
     * Originally made by xzxADIxzx.
     */
public class YellowControl {
    private static final float width = 150f;
    private static final float padding = 4f;
    private static final float isize = 48f;

    private YellowControlDialog dialog;

    public void build(Group parent){
        Drawable icon = atlas.drawable("status-disarmed");

        dialog = new YellowControlDialog();

        if(mobile){
            YellowUtils.mobileHudButton(icon, () -> {
                if(player.unit() instanceof YellowUnitEntity) dialog.show(player.unit().mounts, ((YellowUnitEntity) player.unit()).spells(), (YellowUnitEntity) player.unit());
            });
            YellowUtils.mobileHudButton(Icon.down, () -> {
                if(player.unit() instanceof YellowUnitEntity){
                    YellowUnitEntity aegis = (YellowUnitEntity) player.unit();
                    aegis.setForceIdle(!aegis.getForceIdle());
                }
            });
            YellowUtils.mobileHudButton(Icon.effect, () -> {
                if(player.unit() instanceof YellowUnitEntity){
                    YellowUnitEntity aegis = (YellowUnitEntity) player.unit();
                    aegis.setEnableAutoIdle(!aegis.getEnableAutoIdle());
                }
            });
            YellowUtils.mobileHudButton(Icon.exit, () -> {
                if(player.unit() instanceof YellowUnitEntity) ((YellowUnitEntity) player.unit()).despawn();
            });
            YellowUtils.mobileHudButton(Icon.warning, () -> {
                if(player.unit() instanceof YellowUnitEntity) player.unit().kill();
            });
        }else{
            ImageButtonStyle style = new ImageButtonStyle(){{
                up = Tex.pane;
                down = Styles.flatDown;
                over = Styles.flatOver;
            }};
            
            parent.fill(cont -> {
                cont.name = "weapon switch";
                cont.defaults().size(width / 2f);
    
                cont.top().right();
                cont.marginRight(width - padding);
    
                cont.button(icon, style, isize, () -> dialog.show(player.unit().mounts, ((YellowUnitEntity) player.unit()).spells(), (YellowUnitEntity) player.unit()));

                cont.visible(() -> player.unit() instanceof YellowUnitEntity && !disableUI);
            });
        }
    }
}
