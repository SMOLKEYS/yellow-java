package yellow.ui.buttons;

import arc.scene.*;
import arc.scene.style.*;
import arc.scene.ui.ImageButton.*;
import mindustry.gen.*;
import mindustry.ui.*;
import yellow.entities.units.entity.*;
import yellow.internal.util.*;
import yellow.ui.buttons.dialogs.*;

import static arc.Core.*;
import static mindustry.Vars.*;

    /**
     * Adds a button anchored to the minimap
     * @author xzxADIxzx
     */
public class YellowControl {
    /** minimap width, not scaled */
    private static final float width = 150f;
    private static final float padding = 4f;
    private static final float isize = 48f;

    private YellowControlDialog dialog;

    public void build(Group parent){
        Drawable icon = atlas.drawable("status-disarmed");

        dialog = new YellowControlDialog(); // do not create new ones until the client loads

        if(mobile){
            YellowUtils.mobileHudButton(icon, () -> {
                if(player.unit() instanceof YellowUnitEntity){
                    dialog.show(player.unit().mounts);
                }
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
                if(player.unit() instanceof YellowUnitEntity){
                    ((YellowUnitEntity) player.unit()).despawn();
                }
            });
        }else{
            //TODO adapt with foo's custom corner ui
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
    
                cont.button(icon, style, isize, () -> dialog.show(player.unit().mounts, (YellowUnitEntity) player.unit()));
    
                // show buttons only when player controls yellow air
                cont.visible(() -> player.unit() instanceof YellowUnitEntity);
            });
        }
    }
}
