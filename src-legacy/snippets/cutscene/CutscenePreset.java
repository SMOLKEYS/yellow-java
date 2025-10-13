package yellow.cutscene;

import arc.*;
import arc.func.*;
import arc.math.*;
import mindustry.*;
import mindustry.gen.*;

public class CutscenePreset{
    public static CutscenePreset testCutscene = new CutscenePreset(
            "test-cutscene",
            () -> true,
            () -> new CutsceneController[]{
                    Controllers.delay(60 * 2f),
                    Controllers.moveCameraTo(() -> Vars.player.unit().team.cores().first(), 60 * 1f, Interp.smooth),
                    Controllers.delay(60 * 2f),
                    Controllers.moveCameraTo(() -> Vars.player.unit(), 60 * 1f, Interp.smooth),
                    Controllers.delay(60 * 1f),
                    Controllers.coverScreen(false),
                    Controllers.moveCameraTo(() -> Vars.player.unit().team.cores().first(), 60 * 0.2f, Interp.linear),
                    Controllers.resetCurtains(false),
                    Controllers.delay(60 * 1f),
                    Controllers.resizeCurtains(0.75f, 60 * 4f, Interp.smooth, true),
                    Controllers.changeZoom(5f, 60 * 5f, Interp.smooth),
                    Controllers.delay(60 * 2f),
                    Controllers.run(() -> {
                        Vars.player.unit().team.cores().first().kill();
                        Sounds.wind3.play(400);
                    }),
                    Controllers.hideCurtains(60 * 0.3f, Interp.smooth, false)
            }
    );

    public String name;
    public Boolp conditions;
    public Prov<CutsceneController<?>[]> controllers;

    public final int id = IdProvider.nextId("cutscenes");

    public CutscenePreset(String name, Boolp conditions, Prov<CutsceneController<?>[]> controllers){
        this.name = name;
        this.conditions = conditions;
        this.controllers = controllers;
    }

    public void run(){
        if(!canRun()) return;

        setRun(true);
        YellowVars.cutscenes.queueCutscene(this);
    }

    public boolean canRun(){
        return isMet() && !wasRun();
    }

    public void setRun(boolean run){
        Core.settings.put("cutscene-" + name + "-wasrun", run);
    }

    public boolean wasRun(){
        return Core.settings.getBool("cutscene-" + name + "-wasrun", false);
    }

    public boolean isMet(){
        return conditions.get();
    }
}
