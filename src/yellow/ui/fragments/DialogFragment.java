package yellow.ui.fragments;

import arc.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.input.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.ui.*;
import yellow.compat.*;
import yellow.ui.*;

public class DialogFragment implements CommonFragment{

    private Table parent, box;
    private FLabel label;
    private String[] activeSet;
    private int index, id;
    private boolean active, initialized;
    private float boxWidth, boxHeight;

    @Override
    public void build(Group parent){
        boxWidth = 900;
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
                diag.add("[ NEXT ]").visible(() -> label.hasEnded()).left().bottom().update(l -> l.color.set(Color.gray).lerp(Color.lightGray, Mathf.absin(Time.globalTime, 7f, 1f)));
            }).size(boxWidth, boxHeight);
        });

        initialized = true;
    }

    public void progress(){
        if(!active || !initialized) return;

        if(label.hasEnded()){
            if(index >= activeSet.length - 1){
                stop();
            }else{
                label.restart(activeSet[++index]);
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

    public void initiate(String... set){
        if(active || !initialized) return;
        activeSet = set;
        active = true;
        label.restart(activeSet[index]);
        box.actions(Actions.translateBy(0f, calculateHeightTranslation(boxHeight), 0.5f, Interp.fade));
    }

    public void stop(){
        if(!initialized) return;
        activeSet = null;
        active = false;
        index = 0;
        label.restart("");
        box.actions(Actions.translateBy(0f, -calculateHeightTranslation(boxHeight), 0.3f, Interp.fade));
    }

    private float calculateHeightTranslation(float h){
        return h * 1.9f;
    }

    public static class Dialogue{
        public String name;
        public DialogueIndex[] indexes;

        public static class DialogueIndex{
            public @Nullable String name, text;
            public boolean autoSkip;
            public int autoSkipTime;
        }
    }
}
