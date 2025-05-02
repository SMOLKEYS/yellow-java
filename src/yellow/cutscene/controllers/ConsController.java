package yellow.cutscene.controllers;

import arc.func.*;
import yellow.cutscene.*;

public class ConsController extends CutsceneController<ConsController>{

    public Cons<Object> cons;

    private Object received;

    @Override
    public ConsController self(Cons<ConsController> cons){
        cons.get(this);
        return this;
    }

    @Override
    public void fire(){
        cons.get(received);
    }

    @Override
    public void receive(CutsceneController<?> sender){
        received = sender.data();
    }

    @Override
    public void reset(){
        super.reset();
        cons = null;
        received = null;
    }
}
