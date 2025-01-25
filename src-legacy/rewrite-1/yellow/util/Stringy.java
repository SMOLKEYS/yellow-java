package yellow.util;

import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import mindustry.world.meta.*;

public class Stringy{

    static String collection = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-";
    static Rand r = new Rand();

    /** Accepts a list of strings and returns the first non-null one. Returns null if the entire list is all nulls. */
    public static String alternative(String... inputs){
        for(String s: inputs){
            if(s != null) return s;
        }
        return null;
    }

    /** Returns a random char from the inputted string. */
    public static char random(String input){
        return random(input, Mathf.rand);
    }

    /** Returns a random char from the inputted string using the specified random source. */
    public static char random(String input, Rand rand){
        return input.charAt(rand.random(0, input.length() - 1));
    }

    public static float handleNumber(String input){
        StringBuilder s = new StringBuilder();
        boolean finished = false;

        for(int i = 0; i < input.length(); i++){
            char ind = input.charAt(i);
            if((Character.isDigit(ind) || ind == '.') && !finished){
                s.append(ind);
            }else{
                finished = true;
            }
        }

        return Strings.parseFloat(s.toString());
    }

    public static String formatStat(Stat stat, Object text, @Nullable Color wrapColor){
        if(wrapColor != null){
            return "[lightgray]" + stat.localized() + ":[] [#" + wrapColor + "]" + text + "[]";
        }else{
            return "[lightgray]" + stat.localized() + ":[] " + text;
        }
    }

    public static String generateId(int amount){
        StringBuilder c = new StringBuilder(amount);
        for(int i = 0; i < amount; i++) c.append(random(collection, r));
        return c.toString();
    }

    public static <T> String prettyPrint(T[] arr, boolean enclose){
        if(arr.length == 0){
            return enclose ? "[]" : "";
        }

        StringBuilder buffer = new StringBuilder(32);
        if(enclose) buffer.append('[');
        buffer.append(arr[0]);
        for(int i = 1; i < arr.length; i++){
            buffer.append(", ");
            buffer.append(arr[i]);
        }
        if(enclose) buffer.append(']');

        return buffer.toString();
    }

    public static <T> String prettyPrint(Iterable<T> iterator, boolean enclose){
        StringBuilder buffer = new StringBuilder(32);
        if(enclose) buffer.append('[');
        int prog = 0;
        for(T t: iterator){
            if(prog != 0) buffer.append(", ");
            buffer.append(t);
            prog++;
        }
        if(enclose) buffer.append(']');

        return buffer.toString();
    }
}
