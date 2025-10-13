package yellow.spec.stage;

import arc.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import arc.util.serialization.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import yellow.*;
import yellow.spec.*;
import yellow.util.*;

public class S1 extends StageEntry{

    private final String[] texts = {
            "reform",
            "absolution",
            "deduction",
            "incubation",
            "reformation",
            "abscondance",
            "deduplication",
            "incineration",
            "retaliation"
    };

    private final String[] finTexts = {
            "you are not alone",
            "do you remember?",
            "mistaken",
            "forgotten",
            "abandoned",
            "it watches"
    };

    private final FloatSeq map = new FloatSeq();
    private float ct, mpos, mintvf;
    private final Interval mintv = new Interval();

    private final IntSet trackedInts = new IntSet();

    public S1(){
        duration = 66.5f*60f;
    }

    @Override
    public void init(){
        atTime(60*53f, ActEntry.actOnce(() -> {
            if(Vars.state.isPlaying()){
                Unit u = Vars.player.unit();

                if(u != null){
                    u.vel().setZero();
                    if(u.type() != UnitTypes.alpha) Chaos.switchPlayerUnit(UnitTypes.alpha);
                }

                Core.camera.position.set(Vars.player);
            }

            Chaos.addInputLock();
            Chaos.blockControl();
            Chaos.eraseHud();
            Chaos.eraseMenu();

            mpos = Chaos.getMusicPos();
            mintvf = 60f * Mathf.random(0.07f, 0.6f);
        }), () -> {
            Chaos.hideAllDialogs(true);

            trackedInts.each(i -> YellowVars.ltfrag.reconfigure(i, Mathf.chance(0.3) ? Base64Coder.encodeString(Structs.random(finTexts)) : Structs.random(finTexts), Icon.eye, false));
            if(mintv.get(mintvf)) Chaos.setMusicPos(mpos);
        });

        onceAtTime(60*57f, () -> {
            Chaos.kickFromSave();
            Chaos.stopSoundControl();
            Chaos.stopAudioBus();

            Events.run(Trigger.draw, () -> {
                Draw.z(Layer.max);
                Draw.color(Color.black);
                Fill.rect(0, 0, 8 * 15000, 8 * 15000);
            });
        });

        onceAtTime(60*58.9f, Chaos::eraseUI);

        onceAtTime(60*60f, () -> {
            FConfigStore.remember();
            FConfig.defaultSpeedPerChar = 0.1f;
            Core.scene.root.fill(table -> {
                table.center();
                table.defaults().center();

                table.add(new FLabel("You chose to do this.\n...\n\nGood luck.")).labelAlign(Align.center).wrapLabel(false);
            });
            FConfigStore.restore();
        });

        onceAtTime(60*65.5f, Chaos::eraseUI);

        onceAtEnd(() -> {
            Chaos.next();
            Core.settings.forceSave();
            Core.app.exit();
        });

        buildMap();
    }

    @Override
    public void begin(){
        if(Yellow.debug) YellowVars.notifrag.showPersistentNotification("zero began the");
    }

    @Override
    public boolean conditions(){
        return Core.input.justTouched() && SafeSettings.getBool("yellow-enable-special-stages", false);
    }

    @Override
    public boolean skip(){
        return Chaos.stageIndex() > 0;
    }

    @Override
    public StageEntry next(){
        return new S2();
    }

    @Override
    public void update(float percent){
        if(time >= ct && !map.isEmpty()){
            ct = map.removeIndex(0);
            trackedInts.add(YellowVars.ltfrag.add(Structs.random(texts), Icon.bookOpen, true));
            ((ClientLauncher) Vars.platform).resize(Core.graphics.getWidth() + Mathf.range(90), Core.graphics.getHeight() + Mathf.range(90));
        }
    }

    void buildMap(){
        int c = Mathf.random(30, 90);
        for(int j = 0; j < c; j++){
            map.add(Mathf.random(60*5, acts.peek().actTime));
        }
        map.sort();
        ct = map.removeIndex(0);
    }
}
