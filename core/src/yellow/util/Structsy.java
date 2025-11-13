package yellow.util;

import arc.func.*;
import arc.util.*;

import java.lang.reflect.*;
import java.util.*;

public class Structsy{

    @SafeVarargs
    public static <T> void eachIndexed(Cons2<T, Integer> cons, T... arr){
        for(int i = 0; i < arr.length; i++) cons.get(arr[i], i);
    }

    public static <T> void eachIndexed(Cons2<T, Integer> cons, Iterable<T> iterator){
        int i = 0;
        for(T t: iterator){
            cons.get(t, i);
            i++;
        }
    }

    /** Returns the distance between two elements in the specified array. */
    public static <T> int distance(T[] arr, T item1, T item2){
        int i1 = -1, i2 = -1;

        for(int i = 0; i < arr.length; i++){
            T value = arr[i];
            //java, fuck you
            if(Objects.equals(value, item1)) i1 = i;
            if(Objects.equals(value, item2)) i2 = i;
        }

        return Math.abs(i1 - i2);
    }

    /** Basically {@link Structs#indexOf(Object[], Object)}, but uses {@link Objects#equals(Object, Object)} for comparisons. */
    public static <T> int indexOf(T[] arr, T value){
        for(int i = 0; i < arr.length; i++) if(Objects.equals(arr[i], value)) return i;
        return -1;
    }

    public static <T> T[] mergeArray(Class<T> clazz, T[] first, T[] second){
        T[] arr = Reflect.newArray(clazz, first.length + second.length);
        System.arraycopy(first, 0, arr, 0, first.length);
        System.arraycopy(second, 0, arr, first.length, second.length);
        return arr;
    }
}
