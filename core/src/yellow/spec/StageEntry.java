package yellow.spec;

import arc.struct.*;
import arc.util.*;
import yellow.*;

public abstract class StageEntry{

    private static final Seq<ActEntry> tmpSeq = new Seq<>();

    boolean trigger, began;

    protected boolean complete;
    protected float time, duration;

    public final Seq<ActEntry> acts = new Seq<>();

    public final void pos(float p){
        if(!Yellow.debug) return;
        time = p;
    }


    public final void atTime(float t, Runnable... post){
        tmpSeq.clear();
        for(Runnable runnable : post){
            if(runnable instanceof ActEntry a){
                a.actTime = t;
                acts.add(a);
                continue;
            }
            tmpSeq.add(new ActEntry(t, runnable));
        }

        acts.add(tmpSeq);
    }

    public final void onceAtTime(float t, Runnable... post){
        tmpSeq.clear();
        for(Runnable runnable : post){
            if(runnable instanceof ActEntry a){
                a.actTime = t;
                a.once = true;
                acts.add(a);
                continue;
            }
            tmpSeq.add(new ActEntry(t, runnable){{
                once = true;
            }});
        }

        acts.add(tmpSeq);
    }

    public final void atEnd(Runnable... post){
        tmpSeq.clear();
        for(Runnable runnable : post){
            if(runnable instanceof ActEntry a){
                a.actTime = duration;
                acts.add(a);
                continue;
            }
            tmpSeq.add(new ActEntry(duration, runnable));
        }

        acts.add(tmpSeq);
    }

    public final void onceAtEnd(Runnable... post){
        tmpSeq.clear();
        for(Runnable runnable : post){
            if(runnable instanceof ActEntry a){
                a.actTime = duration;
                a.once = true;
                acts.add(a);
                continue;
            }
            tmpSeq.add(new ActEntry(duration, runnable));
        }

        acts.add(tmpSeq);
    }

    public void act(float delta){
        if(complete) return;

        time += delta;

        acts.each(e -> {
            if(time >= e.actTime) e.run();
        });

        complete = time >= duration;
        float percent;
        if(complete){
            percent = 1;
        }else{
            percent = time / duration;
        }
        update(percent);
        if(complete) conclude();
    }

    // invoked before client fully loads
    public void init(){

    }

    // invoked when the conditions for this stage are met
    public void begin(){

    }

    // invoked once the stage timer ends
    public void conclude(){

    }

    // invoked right after client fully loads
    public void load(){

    }

    protected void update(float percent){

    }

    public abstract boolean conditions();

    public abstract boolean skip();

    @Nullable
    public abstract StageEntry next();

    public static class ActEntry implements Runnable{
        public float actTime;
        public Runnable actRun;
        public boolean once;
        private boolean wasRun;

        public ActEntry(float actTime, Runnable actRun){
            this.actTime = actTime;
            this.actRun = actRun;
        }

        public static ActEntry act(Runnable r){
            return new ActEntry(0f, r);
        }

        public static ActEntry actOnce(Runnable r){
            return new ActEntry(0f, r){{
                once = true;
            }};
        }

        @Override
        public void run(){
            if(once && wasRun) return;
            actRun.run();
            wasRun = true;
        }
    }

    public enum ProgressionType{
        // timer progression, events occur in a generally fixed order at specific times
        timer,
        // level progression, events progress after completing certain requirements
        levels
    }
}
