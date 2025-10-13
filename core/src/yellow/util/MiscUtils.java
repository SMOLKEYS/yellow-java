package yellow.util;

import arc.func.*;

import java.lang.reflect.*;

public class MiscUtils{

    public static <T> T apply(T input, Cons<T> cons){
        cons.get(input);
        return input;
    }

    public static void eachClassField(boolean includeInherited, Class<?> target, Class<?> filter, Cons<Field> cons){
        for(Field field : (includeInherited ? target.getFields() : target.getDeclaredFields())){
            if(field.getType() == filter) cons.get(field);
        }
    }

    public static void eachObjectField(boolean includeInherited, Object target, Class<?> filter, Cons2<Field, Object> cons){
        for(Field field : (includeInherited ? target.getClass().getFields() : target.getClass().getDeclaredFields())){
            if(field.getType() == filter) cons.get(field, target);
        }
    }
}
