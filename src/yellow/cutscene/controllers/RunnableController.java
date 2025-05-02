package yellow.cutscene.controllers;

import arc.func.*;
import yellow.cutscene.*;

public class RunnableController extends CutsceneController<RunnableController>{

    public Runnable run;

    @Override
    public RunnableController self(Cons<RunnableController> cons){
        cons.get(this);
        return this;
    }

    @Override
    public void fire(){
        if(run != null) run.run();
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void reset(){
        super.reset();
        run = null;
    }
}
