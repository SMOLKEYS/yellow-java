package yellow.ui.fragments;

import arc.func.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ui.*;
import yellow.ui.*;

public class LoadTextFragment implements CommonFragment{

    private Table wt;
    private final Rand rand = new Rand(9001);
    private final IntSeq ints = new IntSeq();

    @Override
    public void build(Group parent){
        parent.fill(t -> {
            wt = t;
            t.name = "load text";
            t.visible(() -> true);
            t.center().right();
            t.defaults().right(); //please
        });
    }

    public int add(String text, Drawable icon, boolean rotateIcon){
        int id = rand.random(9999999);
        if(wt.find("lt" + id) != null) return id;

        wt.table(r -> {
            r.name = "lt" + id;
            r.add(text).padRight(15).style(Styles.outlineLabel);
            Image i = r.image(icon).padLeft(6).padRight(7).get();

            if(rotateIcon) i.actions(Actions.originCenter(), Actions.forever(Actions.rotateBy(-360, 2)));
        }).row();

        ints.add(id);
        return id;
    }

    public void each(Cons<Table> cons){
        ints.each(e -> cons.get(wt.find("lt" + e)));
    }

    public void remove(int id){
        Table idt = wt.find("lt" + id);
        if(idt != null) idt.remove();
        ints.removeValue(id);
    }

    public void reconfigure(int id, String text, @Nullable Drawable icon, boolean rotateIcon){
        Table idt = wt.find("lt" + id);
        if(idt == null) return;

        Label l = (Label) idt.getChildren().get(0);
        Image i = (Image) idt.getChildren().get(1);

        l.setText(text);
        if(icon != null) i.setDrawable(icon);

        if(rotateIcon && !i.hasActions()){
            i.actions(Actions.originCenter(), Actions.forever(Actions.rotateBy(-360, 2)));
        }else if(!rotateIcon){
            i.clearActions();
            i.setRotation(0);
        }
    }

    public void hide(int id, Action... actions){
        hide(id, 0, actions);
    }

    public void hide(int id, float delay, Action... actions){
        Table idt = wt.find("lt" + id);
        if(idt == null) return;

        if(actions.length == 0) idt.remove();

        idt.actions(Actions.delay(delay/60f), Actions.sequence(actions), Actions.remove());
    }

    public void removeAll(){
        ints.clear();
        wt.forEach(e -> {
            if(e.name.startsWith("lt")) e.remove();
        });
    }

    public void reconfigureAll(String text, @Nullable Drawable icon, boolean rotateIcon){
        wt.forEach(id -> {
            if(id.name.startsWith("lt") && id instanceof Table idt){
                Label l = (Label) idt.getChildren().get(0);
                Image i = (Image) idt.getChildren().get(1);

                l.setText(text);
                if(icon != null) i.setDrawable(icon);

                if(rotateIcon && !i.hasActions()){
                    i.actions(Actions.originCenter(), Actions.forever(Actions.rotateBy(-360, 2)));
                }else if(!rotateIcon){
                    i.clearActions();
                    i.setRotation(0);
                }
            }
        });
    }

    public void hideAll(Action... actions){
        hide(0, actions);
    }

    public void hideAll(float delay, Action... actions){
        if(actions.length == 0){
            removeAll();
            return;
        }

        ints.clear();
        wt.forEach(idt -> {
            if(idt.name.startsWith("lt")) idt.actions(Actions.delay(delay/60f), Actions.sequence(actions), Actions.remove());
        });
    }
}
