package yellow.cutscene.controllers;

import arc.func.*;
import mindustry.*;
import mindustry.gen.*;
import yellow.cutscene.*;

/** A controller that immediately makes the player control the specified target. */
public class TransferPlayerController extends CutsceneController<TransferPlayerController>{

    public Unit target;

    @Override
    public TransferPlayerController self(Cons<TransferPlayerController> cons){
        cons.get(this);
        return this;
    }

    @Override
    public void receive(CutsceneController<?> sender){
        if(sender != null && target == null){
            Object ob = sender.data();
            if(ob instanceof Unit u) target = u;
        }
    }

    @Override
    public void fire(){
        if(target != null) Vars.player.justSwitchTo(target);
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void reset(){
        super.reset();
        target = null;
    }
}