package yellow.cutscene;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.struct.*;
import mindustry.*;
import mindustry.gen.*;
import yellow.*;
import yellow.util.*;

public class CutscenePreset{
    public static CutscenePreset testCutscene = new CutscenePreset(
            () -> true,
            Controllers.delay(60*2f),
            Controllers.moveCameraTo(() -> Vars.player.unit().team.cores().first(), 60*1f, Interp.smooth),
            Controllers.delay(60*2f),
            Controllers.moveCameraTo(() -> Vars.player.unit(), 60*1f, Interp.smooth),
            Controllers.delay(60*1f),
            Controllers.moveCameraTo(() -> Vars.player.unit().team.cores().first(), 60*1f, Interp.smooth),
            Controllers.delay(60*1f),
            Controllers.resizeCurtains(0.75f, 60*4f, Interp.smooth, true),
            Controllers.changeZoom(5f, 60*5f, Interp.smooth),
            Controllers.delay(60*2f),
            Controllers.run(() -> {
                Vars.player.unit().team.cores().first().kill();
                Sounds.wind3.play(400);
            }),
            Controllers.resizeCurtains(0.22f, 60*0.3f, Interp.smooth, false)
    );

    public Boolp conditions;
    public Seq<CutsceneController<?>> controllers = new Seq<>();

    public CutscenePreset(Boolp conditions, CutsceneController<?>... controllers){
        this.conditions = conditions;
        this.controllers.addAll(controllers);
    }

    public CutscenePreset(Boolp conditions, Seq<CutsceneController<?>> controllers){
        this.conditions = conditions;
        this.controllers = controllers;
    }

    public void run(){
        YellowVars.cutscenes.queueCutscene(this);
    }

    public boolean isMet(){
        return conditions.get();
    }
}
