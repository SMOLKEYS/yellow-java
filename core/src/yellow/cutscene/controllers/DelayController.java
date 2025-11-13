package yellow.cutscene.controllers;

import arc.func.*;
import arc.util.*;
import yellow.cutscene.*;

public class DelayController extends CutsceneController<DelayController>{

    public float time;

    private float curTime;

    public DelayController(){
        parallelize = false; //why even
    }

    @Override
    public DelayController self(Cons<DelayController> cons){
        cons.get(this);
        return this;
    }

    @Override
    public void update(Cutscenes mainController){
        curTime += Time.delta;
    }

    @Override
    public boolean isFinished(){
        return curTime >= time;
    }

    @Override
    public void reset(){
        super.reset();
        time = curTime = 0f;
    }
}
