package yellow.util;

import arc.util.*;
import mindustry.*;

import java.lang.reflect.*;

@SuppressWarnings("UnusedReturnValue")
public class SafeReflect{

    public static <T> T get(Field field){
        try{
            return Reflect.get(field);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T get(Object object, Field field){
        try{
            return Reflect.get(object, field);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T get(Class<?> type, Object object, String name){
        try{
            return Reflect.get(type, object, name);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T get(Object object, String name){
        try{
            return Reflect.get(object, name);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T get(Class<?> type, String name){
        try{
            return Reflect.get(type, name);
        }catch(Exception e){
            return null;
        }
    }

    public static void set(Class<?> type, Object object, String name, Object value){
        try{
            Reflect.set(type, object, name, value);
        }catch(Exception ignored){

        }
    }

    public static void set(Object object, Field field, Object value){
        try{
            Reflect.set(object, field, value);
        }catch(Exception ignored){

        }
    }

    public static void set(Object object, String name, Object value){
        try{
            Reflect.set(object, name, value);
        }catch(Exception ignored){

        }
    }

    public static void set(Class<?> type, String name, Object value){
        try{
            Reflect.set(type, name, value);
        }catch(Exception ignored){

        }
    }

    public static <T> T invoke(Class<?> type, Object object, String name, Object[] args, Class<?>... parameterTypes){
        try{
            return Reflect.invoke(type, object, name, args, parameterTypes);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T invoke(Class<?> type, String name, Object[] args, Class<?>... parameterTypes){
        try{
            return Reflect.invoke(type, name, args, parameterTypes);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T invoke(Class<?> type, String name){
        try{
            return Reflect.invoke(type, name);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T invoke(Object object, String name, Object[] args, Class<?>... parameterTypes){
        try{
            return Reflect.invoke(object, name, args, parameterTypes);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T invoke(Object object, String name){
        try{
            return Reflect.invoke(object, name);
        }catch(Exception e){
            return null;
        }
    }

    public static Class<?> clazz(String name){
        try{
            return Vars.mods.mainLoader().loadClass(name);
        }catch(Exception e){
            return null;
        }
    }

    public static <T> T make(String type){
        try{
            return Reflect.make(type);
        }catch(Exception e){
            return null;
        }
    }
}
