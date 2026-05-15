package yellow.ui.fragments;

import arc.flabel.*;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.pooling.Pool.*;
import arc.util.pooling.*;
import mindustry.ui.*;
import yellow.ui.*;

public class StylePanelFragment implements CommonFragment{

    private Table panel;

    @Override
    public void build(Group parent){
        parent.fill(s -> {
            s.top().right();
            s.marginTop(150).marginRight(10);

            s.table(Styles.black3, uh -> {
                uh.pane(pan -> {
                    panel = pan;
                    pan.top().left();
                    pan.margin(10f);
                }).grow().scrollX(false).scrollY(false);
                uh.visible(() -> panel != null && panel.hasChildren());
            }).size(250, 470);
        });
    }

    public void entry(String name, float time){
        StyleEntry en = Pools.obtain(StyleEntry.class, StyleEntry::new);
        en.set(name, time);
        panel.add(new FLabel(en.name)).wrap().update(lb -> {
            en.timer -= Time.delta;
            if(en.timer <= 0f){
                panel.removeChild(lb);
                Pools.free(en);
            }
        }).growX().get().setStyle(Styles.outlineLabel);
        panel.getChildren().reverse();
        panel.row();
    }

    public static class StyleEntry implements Poolable{
        public String name;
        public float timer;

        public StyleEntry set(String name, float timer){
            this.name = name;
            this.timer = timer;
            return this;
        }

        @Override
        public void reset(){
            name = null;
            timer = 0f;
        }
    }
}
