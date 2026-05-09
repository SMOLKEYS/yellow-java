package yellow.ui.fragments;

import arc.*;
import arc.audio.*;
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
    private Cell<FLabel> labelCell;
    private TextEntry[] activeSet;
    private int index, id;
    private boolean active, initialized, locked = false, hideHint = true;
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
                labelCell = diag.add(label = new FLabel("")).style(Styles.outlineLabel).with(f -> {
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
                diag.label(() -> Vars.mobile ? "@yellow.dialog-hint-mobile" : "@yellow.dialog-hint").visible(() -> timer.get() && !atEnd() && !hideHint).left().bottom().update(l -> l.color.set(Color.gray).lerp(Color.gray, Mathf.absin(Time.globalTime, 7f, 1f)));
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
                label.restart(activeSet[++index].text);
                timer.reset();
            }
        }else{
            label.skipToTheEnd();
        }
    }

    public void previous(){
        if(!active || !initialized) return;

        if(label.hasEnded()){
            label.restart(activeSet[--index].text);
        }
    }

    public void lock(boolean lock){
        locked = lock;
    }

    public void initiate(TextEntry... set){
        initiate(false, set);
    }

    public void initiate(boolean lock, TextEntry... set){
        if(active || !initialized) return;
        activeSet = set;
        active = true;
        locked = lock;
        label.restart(activeSet[index].text);
        timer.reset();
        box.actions(Actions.translateBy(0f, calculateHeightTranslation(boxHeight), 0.5f, Interp.fade));
    }

    public void reinitiate(TextEntry... set){
        reinitiate(locked, set);
    }

    public void reinitiate(boolean lock, TextEntry... set){
        if(!active || !initialized) return;
        activeSet = set;
        locked = lock;
        index = 0;
        timer.reset();
        label.restart(activeSet[index].text);
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
        activeSet[index].text = newValue;
        label.restart(activeSet[index].text);
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

    public void hideHint(boolean hideHint){
        this.hideHint = hideHint;
    }

    public void setAlignment(int align){
        label.setAlignment(align);
        labelCell.align(align);
    }

    private float calculateHeightTranslation(float h){
        return h * 1.9f;
    }


    /**
     * A dialogue box text entry. Contains extra listeners and parameters to precisely modify
     * dialogue box behavior.
     */
    public static class TextEntry{
        /** The name to be displayed. A null value automatically uses the last set name, for convenience purposes. */
        public @Nullable String name;
        /** The text to be typed out. A null value will reuse the last typed text. */
        public @Nullable String text;
        /** Sound played when this text is displayed. Useful for voiced dialogue. */
        public @Nullable Sound sound;
        /**
         * Interrupt or "stutter" text which immediately replaces the existing text.
         * You probably know what this does if you've played enough visual novels.
         */
        public @Nullable String[] interruptText;
        /** The font used for this text. A null value automatically uses the last set font. */
        public @Nullable Font font;
        /** The font scaling of this text. */
        public float scaling = 1f;
        /** Delay before this text entry is automatically skipped. -1 to disable. */
        public float autoProgress = -1f;
        /** Alignment of the text, using bit flags. Use {@link Align} to find the preferred values. */
        public int alignment = Align.topLeft;
        /** Whether this text can be manually skipped with an enter click. */
        public boolean canSkipByClick = true;
        /**
         * Whether this text can be automatically skipped when passed through during a continuous skip.
         * Ignored when a choice menu is brought up.
         */
        public boolean canSkipBySkip = true;
        /** Choices displayed to the player. Optional. */
        public @Nullable EntrySelection selection;

        /** Returns a simple text entry. */
        public static TextEntry with(String tex){
            return new TextEntry(){{
                text = tex;
            }};
        }

        /** Returns an array of text entries from a set of strings. */
        public static TextEntry[] fromSimpleStrings(String... texts){
            TextEntry[] set = new TextEntry[texts.length];
            for(int i = 0; i < texts.length; i++){
                int fi = i;
                set[i] = new TextEntry(){{
                    text = texts[fi];
                }};
            }
            return set;
        }

        public static class EntrySelection extends ObjectMap<String, TextEntry[]>{
            public void addEntry(String choice, TextEntry... follow){
                put(choice, follow);
            }
        }
    }
}
