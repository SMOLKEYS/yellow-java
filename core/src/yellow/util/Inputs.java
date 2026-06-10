package yellow.util;

import arc.*;
import arc.input.*;
import arc.struct.*;
import arc.util.*;

public class Inputs{

    private static final Seq<KeyCode> tmpSeqCode = new Seq<>(KeyCode.class);

    public static boolean keyDown(KeyCode... keys){
        for(KeyCode k : keys){
            if(!Core.input.keyDown(k)) return false;
        }
        return true;
    }

    public static boolean keyDown(KeyBind... keys){
        for(KeyBind k : keys){
            if(!Core.input.keyDown(k)) return false;
        }
        return true;
    }

    public static InputSequence keySequence(float time, boolean repeat, KeyCode... keys){
        return new InputSequence((long) (time / 60 * 1000), repeat, keys);
    }

    public static InputSequence keySequenceFromString(float time, boolean repeat, String input){
        return keySequence(time, repeat, toKeyCodes(input));
    }

    public static KeyCode[] toKeyCodes(String input){
        tmpSeqCode.clear();

        for(char c : input.toCharArray()){
            String code = String.valueOf(c);

            switch(c){
                case ' ' -> code = "space";
            }

            try{
                tmpSeqCode.add(KeyCode.valueOf(code));
            }catch(Exception e){
                Log.err("Invalid keycode '@'", code);
            }
        }

        return tmpSeqCode.toArray();
    }

    /** Listens for a sequence of specific key inputs. */
    public static class InputSequence{
        public boolean repeat;

        private KeyCode[] keys;
        private long maxTime;

        protected int index = 0;

        private long lastTime;

        public InputSequence(long maxTime, boolean repeat, KeyCode... keys){
            this.keys = keys;
            this.repeat = repeat;
            this.maxTime = maxTime;

            lastTime = Time.millis();
        }

        private boolean completed(){
            return index >= keys.length;
        }

        /** @return true if the sequence was successfully completed since the last reset(); resets the listener if true, but only if {@link #repeat} is also true. */
        public boolean poll(){
            boolean result = get();
            if(result && repeat) reset();
            return result;
        }

        public boolean get(){
            if(completed()){
                return true;
            }

            if(Time.timeSinceMillis(lastTime) > maxTime){
                reset();
                return false;
            }

            if(Core.input.keyTap(keys[index])){
                index++;
                resetTimer();
                return completed();
            }

//            if(Core.input.keyTap(KeyCode.anyKey)){
//                reset();
//                return false;
//            }


            return completed();
        }

        public void resetTimer(){
            lastTime = Time.millis();
        }

        public void reset(){
            index = 0;
            lastTime = Time.millis();
        }
    }
}
