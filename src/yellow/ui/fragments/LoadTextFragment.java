package yellow.ui.fragments;

import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.ui.*;
import yellow.ui.*;

public class LoadTextFragment implements CommonFragment{

    private Table wt;

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

    public boolean add(int id, String text, Drawable icon, boolean rotateIcon){
        if(wt.find("lt" + id) != null) return false;

        wt.table(r -> {
            r.name = "lt" + id;
            r.add(text).padRight(15).style(Styles.outlineLabel);
            Image i = r.image(icon).padRight(10).get();

            if(rotateIcon) i.actions(Actions.originCenter(), Actions.forever(Actions.rotateBy(-360, 2)));
        }).row();

        return true;
    }

    public void remove(int id){
        Table idt = wt.find("lt" + id);
        if(idt != null) idt.remove();
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

        Time.run(delay, () -> {
            if(actions.length == 0) idt.remove();

            idt.actions(Actions.sequence(actions), Actions.remove());
        });
    }
}
