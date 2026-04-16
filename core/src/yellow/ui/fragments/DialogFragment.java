package yellow.ui.fragments;

import arc.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.input.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.ui.Label.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.*;
import yellow.compat.*;
import yellow.ui.*;

public class DialogFragment implements CommonFragment{

    private Table parent, box;
    private FLabel label;
    private String[] activeSet;
    private int index, id;
    private boolean active, initialized, locked = false;
    private float boxWidth, boxHeight;

    private final Timekeeper timer = Timekeeper.ofSeconds(10f);
    private final ObjectMap<Font, LabelStyle> fontStyles = new ObjectMap<>();

    @Override
    public void build(Group parent){
        boxWidth = 900f;
        boxHeight = 200f;

        parent.fill(t -> {
            this.parent = t;

            t.setTranslation(0f, -calculateHeightTranslation(boxHeight));

            t.bottom().defaults().bottom();

            t.table(Tex.pane, diag -> {
                box = diag;

                diag.update(() -> {
                    diag.setOrigin(Align.center);
                });

                diag.margin(20f);
                diag.top().left();
                diag.add(label = new FLabel("")).style(Styles.outlineLabel).with(f -> {
                    f.setWrap(true);

                    f.update(() -> {
                        if(Core.input.keyTap(KeyCode.enter) && active){
                            if(!f.hasEnded()){
                                f.skipToTheEnd(false);
                                return;
                            }

                            progress();
                        }
                    });
                    f.setTypingListener(new AndroidCompatibilityClasses.AFListener(){
                        @Override
                        public void event(String event){
                            //TODO dunno wtf to do with this yet
                            if(event.startsWith("face:")){
                                //image[0].setDrawable(Core.atlas.find("noveldustry-" + event.substring("face:".length())));
                            }
                        }
                    });
                }).labelAlign(Align.topLeft).top().left().grow();

                //andord
                diag.clicked(() -> {
                    if(active) progress();
                });

                diag.row();
                diag.add("[ NEXT ]").visible(() -> label.hasEnded()).left().bottom().update(l -> {
                    l.color.set(Color.gray).lerp(Color.lightGray, Mathf.absin(Time.globalTime, 7f, 1f));
                    l.setText(atEnd() ? "[ END ]" : "[ NEXT ]");
                });
                diag.row();
                diag.label(() -> Vars.mobile ? "@yellow.dialog-hint-mobile" : "@yellow.dialog-hint").visible(() -> timer.get() && !atEnd()).left().bottom().update(l -> l.color.set(Color.gray).lerp(Color.gray, Mathf.absin(Time.globalTime, 7f, 1f)));
            }).size(Vars.mobile ? boxWidth * 0.65f : boxWidth, boxHeight);
        });

        initialized = true;
    }

    private boolean atEnd(){
        if(!initialized) return false;
        if(!active) return true;
        return index >= activeSet.length - 1;
    }

    public void progress(){
        if(!active || !initialized) return;

        if(locked && atEnd()) return;

        if(label.hasEnded()){
            if(atEnd()){
                stop();
            }else{
                label.restart(activeSet[++index]);
                timer.reset();
            }
        }else{
            label.skipToTheEnd();
        }
    }

    public void previous(){
        if(!active || !initialized) return;

        if(label.hasEnded()){
            label.restart(activeSet[--index]);
        }
    }

    public void lock(boolean lock){
        locked = lock;
    }

    public void initiate(String... set){
        initiate(false, set);
    }

    public void initiate(boolean lock, String... set){
        if(active || !initialized) return;
        activeSet = set;
        active = true;
        locked = lock;
        label.restart(activeSet[index]);
        timer.reset();
        box.actions(Actions.translateBy(0f, calculateHeightTranslation(boxHeight), 0.5f, Interp.fade));
    }

    public void reinitiate(String... set){
        reinitiate(locked, set);
    }

    public void reinitiate(boolean lock, String... set){
        if(!active || !initialized) return;
        activeSet = set;
        locked = lock;
        index = 0;
        timer.reset();
        label.restart(activeSet[index]);
    }

    public void stop(){
        stop(false);
    }

    public void stop(boolean instant){
        if(!initialized) return;
        activeSet = null;
        active = false;
        locked = false;
        index = 0;
        label.restart("");
        if(instant){
            box.setTranslation(0f, -calculateHeightTranslation(boxHeight));
        }else{
            box.actions(Actions.translateBy(0f, -calculateHeightTranslation(boxHeight), 0.3f, Interp.fade));
        }
    }

    public void overwrite(String newValue){
        if(!active || !initialized) return;
        activeSet[index] = newValue;
        label.restart(activeSet[index]);
    }

    public void setFont(Font font){
        LabelStyle ls = fontStyles.get(font);
        if(ls != null){
            label.setStyle(ls);
        }else{
            LabelStyle newLs = new LabelStyle(font, Color.white);
            fontStyles.put(font, newLs);
            label.setStyle(newLs);
        }
    }

    public void setFontScale(float scale){
        label.setFontScale(scale);
    }

    public void setBoxWidth(float boxWidth, float time, @Nullable Interp interpolation){
        if(!active || !initialized) return;
        float moveOffset = this.boxWidth - boxWidth;
        this.boxWidth = boxWidth;
        box.actions(Actions.sizeTo(boxWidth * 0.65f, this.boxHeight, time, interpolation != null ? interpolation : Interp.linear), Actions.translateBy(moveOffset, 0f, 0.3f, Interp.smoother));
    }

    public void setBoxHeight(float boxHeight, float time, @Nullable Interp interpolation){
        if(!active || !initialized) return;
        this.boxHeight = boxHeight;
        box.actions(Actions.sizeTo(this.boxWidth, boxHeight, time, interpolation != null ? interpolation : Interp.linear));
    }

    private float calculateHeightTranslation(float h){
        return h * 1.9f;
    }

}
