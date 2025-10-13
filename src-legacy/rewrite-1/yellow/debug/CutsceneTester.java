package yellow.debug;

import arc.*;
import arc.struct.*;
import mindustry.*;
import mindustry.game.*;

public class CutsceneTester{

    public static Queue<CutsceneController<?>> queue = new Queue<>();
    public static CutsceneController<?> prev, active;
    public static boolean lock = false;

    public static void update(){
        lock = active != null || !queue.isEmpty();

        if(active != null){
            active.update();
            if(active.isFinished()){
                active.onFinish();
                if(prev != null && prev.getPool() != null) prev.getPool().free(prev);
                prev = active;
                active = null;
            }
        }else if(!queue.isEmpty()){
            active = queue.removeFirst();
            active.init();
            active.receive(prev);
        }
    }

    public static void load(){
        Events.run(EventType.Trigger.update, CutsceneTester::update);
        Vars.control.input.addLock(() -> lock);
    }

    public static void addController(CutsceneController<?>... controllers){
        for(CutsceneController<?> controller: controllers) queue.add(controller);
    }
}
