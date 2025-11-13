package yellow.cutscene;

import arc.func.*;
import arc.math.*;
import arc.util.*;
import arc.util.pooling.*;

/** The base class of all controllers. Has a suspiciously intricate amount of documentation.  */
@SuppressWarnings("unused")
public abstract class CutsceneController<T> implements Pool.Poolable{
    @Nullable
    protected Pool<CutsceneController<?>> pool;

    /** If true, this controller will be handled by a separate cutscene runner, but will update alongside the main one. */
    public boolean parallelize = false;

    public abstract T self(Cons<T> cons);

    /** The pool this controller will be returned to when no longer in use. Can be null. */
    @Nullable
    public Pool<CutsceneController<?>> getPool(){
        return pool;
    }

    /** Sets the target pool this controller will be returned to when no longer in use. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setPool(Pool pool){
        this.pool = pool;
    }

    /** Called before all other methods. Can be used for setting up special properties. */
    public void init(){}

    /** When invoked, this controller can receive data from a provided sender.
     * @param sender The provided sender with data this controller can use. Do NOT attempt to retain/cache or use in multithreaded code, as it is immediately reset once this method finishes. */
    public void receive(CutsceneController<?> sender){}

    /** Returns data from this controller that can be used by followup controllers through {@link #receive(CutsceneController)}. Can be null. */
    @Nullable
    public Object data(){
        return null;
    }

    /** Called when this controller is skipped. Reserved for save/load. */
    public void onSkip(){}

    /** Updates this controller. */
    public void update(Cutscenes mainController){}

    /** Called once this controller finishes. Is followed up by {@link #receive(CutsceneController)} and {@link #reset()}. */
    public void onFinish(){}

    /** Returns true if this controller has finished whatever it was designed to do. By default, checks if {@link #progress()} returns 1. */
    public boolean isFinished(){
        return progress() == 1;
    }

    /** Returns either a Linear 0-1 value, or any other value used to determine the progress of this controller.
     * @see #progress(Interp) */
    public float progress(){
        return 0;
    }

    /** Returns a non-Linear, interpolated 0-1 value. Certain types of interpolation may make it return a value above 1. */
    public final float progress(Interp interp){
        return interp.apply(progress());
    }

    /** Allows configuring of the cutscene runner. */
    public void reconfigure(Cutscenes cutscenes){

    }

    /** Runs after {@link #init()}, {@link #receive(CutsceneController)} and {@link #reconfigure(Cutscenes)}. */
    public void fire(){
    }

    public void reset(){
        pool = null;
    }
}
