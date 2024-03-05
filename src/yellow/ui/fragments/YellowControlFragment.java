package yellow.ui.fragments;

import arc.scene.*;
import arc.scene.style.*;
import arc.scene.ui.ImageButton.*;
import mindustry.gen.*;
import mindustry.ui.*;
import yellow.*;
import yellow.entities.units.entity.*;
import yellow.util.*;

import static arc.Core.*;
import static mindustry.Vars.*;

    /**
     * Adds a button anchored to the minimap, or a new row of buttons on mobile.
     * Originally made by xzxADIxzx.
     */
public class YellowControlFragment{
    private static final float width = 150f;
    private static final float padding = 4f;
    private static final float isize = 48f;


    public void build(Group parent){
        Drawable icon = atlas.drawable("status-disarmed");

        if(mobile){
            YellowUtils.mobileHudButton(icon, () -> {
                if(player.unit() instanceof YellowUnitEntity) YellowVars.ui.yellowControl.show(player.unit().mounts, ((YellowUnitEntity) player.unit()).spells(), (YellowUnitEntity) player.unit());
            });
            YellowUtils.mobileHudButton(Icon.down, () -> {
                if(player.unit() instanceof YellowUnitEntity aegis){
                    aegis.setForceIdle(!aegis.getForceIdle());
                }
            });
            YellowUtils.mobileHudButton(Icon.effect, () -> {
                if(player.unit() instanceof YellowUnitEntity aegis){
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
            
            parent.fill(t -> {
                t.name = "weapon switch";
                t.defaults().size(width / 2f);
    
                t.top().right();
                t.marginRight(width - padding);
    
                t.button(icon, style, isize, () -> YellowVars.ui.yellowControl.show(player.unit().mounts, ((YellowUnitEntity) player.unit()).spells(), (YellowUnitEntity) player.unit()));

                t.visible(() -> player.unit() instanceof YellowUnitEntity && !disableUI);
            });
        }
    }
}
