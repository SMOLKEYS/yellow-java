package yellow.input;

import arc.*;
import arc.func.*;
import arc.input.*;
import arc.util.*;

public class KeyTapListener<T> implements CommonKeyListener<T>{
    /** Amount of completed taps. Reset when time reaches 0. */
    public int taps;
    /** Whether this listener has completed, and can be discarded. */
    public boolean completed,
    /** Whether this listener will never be discarded and will indefinitely listen to taps from keys it's assigned to. */
    keep;
    /** The amount of time passed after tapping a key. */
    public float time;
    /** What to run once the tap goal is reached. */
    public Cons<T> success;

    private final KeyCode key;
    private final int totalTaps;
    private final float totalTime;

    public KeyTapListener(KeyCode key, int taps, float time, Cons<T> success){
        this.key = key;
        this.totalTaps = taps;
        this.totalTime = time;
        this.success = success;
    }

    @Override
    public void update(T t){
        time += Time.delta;

        if(time >= totalTime || (!(key == KeyCode.anyKey) && Core.input.keyDown(KeyCode.anyKey) && !Core.input.keyDown(key))) reset();

        if(Core.input.keyTap(key)){
            taps++;
            time = 0f;
            if(taps >= totalTaps){
                success.get(t);
                reset();
                if(!keep) completed = true;
            }
        }
    }

    private void reset(){
        time = 0f;
        taps = 0;
    }

    @Override
    public boolean canRemove(){
        return completed;
    }

    @Override
    public boolean remove(){
        return YellowUpdateCore.keyListeners.remove(this);
    }

    /** Creates a new key tap listener. Discarded when completed unless keep is true. */
    public static <T> KeyTapListener<T> listenTaps(KeyCode key, int taps, float time, boolean keep, Cons<T> success){
        KeyTapListener<T> k = new KeyTapListener<>(key, taps, time, success);
        k.keep = keep;
        YellowUpdateCore.keyListeners.add(k);
        return k;
    }
}
