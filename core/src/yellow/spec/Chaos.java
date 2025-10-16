package yellow.spec;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.ui.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.game.Saves.*;
import mindustry.gen.*;
import mindustry.input.*;
import mindustry.maps.*;
import mindustry.type.*;
import mindustry.ui.fragments.*;
import yellow.spec.stage.*;
import yellow.util.*;

public class Chaos{

    private static final SettingBoundVariable<Integer> stage = new SettingBoundVariable<>("yellow-stage", 0, true);
    private static final FinalLazyValue<StageEntry> curStage = new FinalLazyValue<>();
    private static final InputHandler nullInp = new NullInput();
    private static InputHandler lastInp;
    private static final Boolp b = () -> true;

    public static void init(){
        /*
        StageEntry s = stage();

        if(s == null) return;

        s.init();

        Events.run(ClientLoadEvent.class, () -> {
            s.load();
            Events.run(Trigger.update, () -> {
                if(s.conditions() || s.trigger){
                    s.trigger = true;
                    if(!s.began){
                        s.began = true;
                        s.begin();
                    }
                    s.act(Time.delta);
                }
            });
        });
         */
    }

    public static StageEntry stage(){
        curStage.set(() -> {
            StageEntry s = new S1();
            while(s.skip()) s = s.next();
            return s;
        });
        return curStage.get();
    }

    public static int stageIndex(){
        return stage.get();
    }

    public static void next(){
        stage.set(s -> s + 1);
    }

    public static void prev(){
        stage.set(s -> s - 1);
    }

    public static void reset(){
        stage.set(0);
        Core.settings.put("yellow-enable-special-stages", false);
        Core.app.exit();
    }

    public static void stopAudioBus(){
        Core.audio.musicBus.stop();
        Core.audio.soundBus.stop();
    }

    public static void startAudioBus(){
        Core.audio.musicBus.play();
        Core.audio.soundBus.play();
    }

    public static void stopSoundControl(){
        Music m = SafeReflect.get(Vars.control.sound, "current");
        if(m != null) m.stop();
        SafeReflect.invoke(Vars.control.sound, "reload");

        MiscUtils.eachClassField(false, Musics.class, Music.class, f -> {
            SafeReflect.set(null, f, new Music());
        });

        MiscUtils.apply(Vars.control.sound, s -> {
            s.ambientMusic.clear();
            s.bossMusic.clear();
            s.darkMusic.clear();
        });
    }

    public static void startSoundControl(){
        Musics.load();
        SafeReflect.invoke(Vars.control.sound, "reload");
    }

    public static void hideFrontDialog(boolean instant){
        Dialog d = Core.scene.getDialog();
        Prov<Action> a = SafeReflect.get(Dialog.class, "defaultHideAction");
        if(d != null) d.hide(instant ? Actions.hide() : a != null ? a.get() : Actions.hide());
    }

    public static void hideAllDialogs(boolean instant){
        Prov<Action> a = SafeReflect.get(Dialog.class, "defaultHideAction");
        while(Core.scene.getDialog() != null){
            Core.scene.getDialog().hide(instant ? Actions.hide() : a != null ? a.get() : Actions.hide());
        }
    }

    public static void eraseMenu(){
        Vars.ui.menuGroup.clear();
    }

    public static void restoreMenu(){
        Vars.ui.menufrag = new MenuFragment();
        Vars.ui.menufrag.build(Vars.ui.menuGroup);
    }

    public static void eraseHud(){
        Vars.ui.hudGroup.clear();
    }

    public static void restoreHud(){
        Vars.ui.hudfrag = new HudFragment();
        Vars.ui.hudfrag.build(Vars.ui.hudGroup);
    }

    public static void eraseUI(){
        Core.scene.clear();
        Core.scene.unfocusAll();
    }

    public static void addInputLock(){
        Vars.control.input.addLock(b);
    }

    public static void removeInputLock(){
        Vars.control.input.inputLocks.remove(b);
    }

    public static float getMusicPos(){
        Music m = SafeReflect.get(Vars.control.sound, "current");
        if(m == null) return 0;

        return m.getPosition();
    }

    public static void setMusicPos(float pos){
        Music m = SafeReflect.get(Vars.control.sound, "current");
        if(m == null) return;

        m.setPosition(pos);
    }

    public static void kickFromSave(){
        SaveSlot current = Vars.control.saves.getCurrent();

        if(current != null){
            try{
                current.save();
            }catch(Exception e){
                //e.printStackTrace();
            }
            Vars.logic.reset();
        }
    }

    public static void playMap(Map in){
        if(Vars.state.isPlaying()) kickFromSave();

        Vars.control.playMap(in, new Rules());
    }

    public static void blockControl(){
        if(lastInp instanceof NullInput) return;
        lastInp = Vars.control.input;
        Vars.control.input = nullInp;
    }

    public static void restoreControl(){
        if(lastInp == null) return;
        Vars.control.input = lastInp;
        lastInp = null;
    }

    public static void switchPlayerUnit(UnitType override){
        Unit u = Vars.player.unit();
        if(u != null){
            Vars.player.justSwitchTo(override.spawn(u.team(), u));
        }
    }

    public static class NullInput extends InputHandler{

        @Override
        public void update(){

        }
    }
}
