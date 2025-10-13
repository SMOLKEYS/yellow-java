package yellow.cutscene.controllers;

import arc.func.*;
import arc.math.*;
import arc.util.*;

public class CurtainController extends CutsceneController<CurtainController>{

    public float span;
    public float time;
    public Interp interp = Interp.linear;

    private float curTime, curSpan;

    @Override
    public CurtainController self(Cons<CurtainController> cons){
        cons.get(this);
        return this;
    }

    @Override
    public void init(){
        curSpan = YellowVars.cutscenes.curtains.curtainSpan;
    }

    @Override
    public void update(Cutscenes mainController){
        curTime += Time.delta;
        mainController.curtains.resizeCurtainsInstant(Mathf.lerp(curSpan, span, progress(interp)));
    }

    @Override
    public boolean isFinished(){
        return progress() == 1;
    }

    @Override
    public float progress(){
        return Mathf.clamp(curTime / time);
    }

    @Override
    public void reset(){
        super.reset();
        span = time = curTime = curSpan = 0f;
        interp = Interp.linear;
    }
}
