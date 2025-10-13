package yellow.ui.fragments;

import arc.*;
import arc.scene.*;
import mindustry.*;
import mindustry.ui.*;

public class LivesFragment implements CommonFragment{


    @Override
    public void build(Group parent){
        parent.fill(t -> {
            t.name = "lives counter";
            t.top().right().marginRight(7);

            t.label(() -> {
                if(Vars.player.unit() instanceof MultiLifeUnitEntity e){
                    return Core.bundle.format("yellow.lifecounter", e.lives);
                }else{
                    return "[red]Invalid unit.[]";
                }
            }).style(Styles.outlineLabel);

            t.visible(() -> Vars.player.unit() instanceof MultiLifeUnitEntity);
        });
    }
}
