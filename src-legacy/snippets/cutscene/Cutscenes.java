package yellow.cutscene;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.scene.actions.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.game.*;

public class Cutscenes{
    private final Queue<CutsceneController<?>> controllerQueue = new Queue<>();
    private final Seq<CutsceneController<?>> parallelizedControllers = new Seq<>();
    private CutsceneController<?> currentController;
    private float time = 0f, peak = 60f, cameraScl;

    public CurtainFragment curtains;

    public boolean useCurtains = true;

    public Cutscenes(){
        Boolp control = this::isActive;

        //account for changed input handlers and curtain
        Events.run(EventType.Trigger.update, () -> {
            if(!Vars.control.input.inputLocks.contains(c -> c == control)){
                Vars.control.input.addLock(control);
            }

            time = (control.get() && useCurtains) ? Math.min(time + Time.delta, peak) : Math.max(time - Time.delta, 0f);

            if(curtains != null){
                curtains.transitionValue = Mathy.lerpc(0, 1, time, peak);
            }

            update();
        });
    }

    public void init(){
        curtains = new CurtainFragment();
        curtains.build(Vars.ui.hudGroup);
    }

    public void queueCutscene(CutscenePreset preset){
        queueCutscene(preset.controllers.get());
    }

    public void queueCutscene(Seq<CutsceneController<?>> controllers){
        for(CutsceneController<?> c : controllers){
            controllerQueue.add(c);
        }
        if(isActive()) return;
        currentController = nextController();
        cameraScl = Vars.renderer.getScale();
        hideHud();
    }

    public void queueCutscene(CutsceneController<?>... controllers){
        for(CutsceneController<?> c : controllers){
            controllerQueue.add(c);
        }
        if(isActive()) return;
        currentController = nextController();
        cameraScl = Vars.renderer.getScale();
        hideHud();
    }

    public boolean isActive(){
        return currentController != null || !parallelizedControllers.isEmpty();
    }

    public void hideHud(){
        Vars.ui.hudGroup.actions(Actions.fadeOut(1, Interp.smooth));
    }

    public void showHud(){
        Vars.ui.hudGroup.actions(Actions.fadeIn(1, Interp.smooth));
    }

    public void update(){
        updateLinearControllers();
        updateParallelControllers();
        updateCamera();
    }

    public void updateLinearControllers(){
        if(currentController == null) return;

        if(currentController.isFinished() && controllerQueue.isEmpty()){
            currentController = null;
            showHud();
            curtains.resizeCurtains(0.22f);
            return;
        }

        if(currentController.isFinished()){
            currentController = nextController();
        }

        currentController.update(this);
    }

    public void updateParallelControllers(){
        if(parallelizedControllers.isEmpty()) return;
        parallelizedControllers.each(e -> {
            e.update(this);
            if(e.isFinished()){
                e.onFinish();
                if(e.getPool() != null) e.getPool().free(e);
                parallelizedControllers.remove(e);
            }
        });
    }

    public void setCameraZoom(float v){
        cameraScl = v;
    }

    public void updateCamera(){
        if(isActive()) Vars.renderer.setScale(cameraScl);
    }

    public CutsceneController<?> nextController(){
        CutsceneController<?> next = controllerQueue.removeFirst();

        next.init();

        if(currentController != null){
            currentController.onFinish();
            next.receive(currentController);
        }

        next.fire();
        next.reconfigure(this);
        if(currentController != null && currentController.getPool() != null) currentController.getPool().free(currentController);

        if(next.parallelize){
            parallelizedControllers.add(next);
            CutsceneController<?> n;
            while((n = nextController()).parallelize)
                parallelizedControllers.add(n);

            return n;
        }

        return next;
    }

}
