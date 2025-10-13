package yellow.util;

import arc.*;
import arc.struct.*;
import arc.util.*;
import mindustry.game.EventType.*;

@SuppressWarnings("unused")
public class Timey{
    private static Seq<Tickdown> tickdowns = new Seq<>();

    public static void runLoop(Runnable r, float intervalSeconds){
        Timer.schedule(r, 0, intervalSeconds, -1);
    }

    public static void runFor(Runnable update, float time){
        tickdowns.add(new Tickdown(update, time));
    }

    public static void init(){
        Events.run(Trigger.update, () -> {
            tickdowns.each(Tickdown::update);
            tickdowns.removeAll(Tickdown::done);
        });
    }

    private static class Tickdown{
        private float curTime, time;
        private Runnable run;

        public Tickdown(Runnable run, float time){
            this.run = run;
            this.time = time;
        }

        private void update(){
            if(curTime >= time) return;
            run.run();
            curTime += Time.delta;
        }

        private boolean done(){
            return curTime >= time;
        }
    }
}
