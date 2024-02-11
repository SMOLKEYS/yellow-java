package yellow.input;

import arc.*;
import arc.func.*;
import arc.input.*;
import arc.util.*;
import yellow.internal.*;

public class KeySequenceListener<T> implements CommonKeyListener<T>{
    /** Amount of completed key taps. */
    public int taps;
    /** Whether this listener has completed, and can be discarded. */
    public boolean completed,
    /** Whether this listener will never be discarded and will indefinitely listen to taps from keys it's assigned to. */
    keep;
    /** The amount of time passed after clicking a key. */
    public float time;
    /** What to run once the tap goal is reached. */
    public Cons<T> success;

    private final KeyCode[] keys;
    private final float totalTime;

    public KeySequenceListener(KeyCode[] keys, float time, Cons<T> success){
        this.keys = keys;
        this.totalTime = time;
        this.success = success;
    }

    @Override
    public void update(T t){
        time += Time.delta;

        if(taps >= keys.length){
            reset();
            return;
        }

        if(time >= totalTime || (Core.input.keyTap(KeyCode.anyKey) && !Core.input.keyTap(keys[taps]))) reset();

        if(Core.input.keyTap(keys[taps])){
            taps++;
            time = 0f;
            if(taps >= keys.length){
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

    /** Creates a new key sequence listener from an array of {@link KeyCode}s. Discarded when completed unless keep is true. */
    public static <T> KeySequenceListener<T> listenTaps(KeyCode[] keys, float time, boolean keep, Cons<T> success){
        KeySequenceListener<T> k = new KeySequenceListener<>(keys, time, success);
        k.keep = keep;
        YellowUpdateCore.keyListeners.add(k);
        return k;
    }

    /** Creates a new key sequence listener from a string.
     * Whitespaces must be placed in between every keycode character except keycodes that are more than one character long.
     *
     * @throws IllegalArgumentException if a specified keycode does not exist
     * */
    public static <T> KeySequenceListener<T> listenTaps(String keys, float time, boolean keep, Cons<T> success){
        String[] s = keys.split(" ");
        KeyCode[] codes = new KeyCode[s.length];
        for(int i = 0; i < s.length; i++){
            codes[i] = KeyCode.valueOf(s[i]);
        }
        return listenTaps(codes, time, keep, success);
    }

}
