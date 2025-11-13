package yellow.util;

import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.ui.*;
import arc.struct.*;
import arc.util.*;

import java.lang.reflect.*;

public class MiscUtils{

    public static <T> T apply(T input, Cons<T> cons){
        cons.get(input);
        return input;
    }

    public static void eachClassField(boolean includeInherited, Class<?> target, @Nullable Class<?> filter, Cons<Field> cons){
        for(Field field : (includeInherited ? target.getFields() : target.getDeclaredFields())){
            if(filter == null || field.getType() == filter || field.getType().getSuperclass() == filter) cons.get(field);
        }
    }

    public static void eachObjectField(boolean includeInherited, Object target, @Nullable Class<?> filter, Cons2<Field, Object> cons){
        for(Field field : (includeInherited ? target.getClass().getFields() : target.getClass().getDeclaredFields())){
            if(filter == null || field.getType() == filter || field.getType().getSuperclass() == filter) cons.get(field, target);
        }
    }

    static boolean deepSearch(Seq<Action> seq, Class<? extends Action> act){
        return seq.contains(s -> {
            if(s.getClass() == act) return true;
            return s instanceof ParallelAction plr && deepSearch(plr.getActions(), act);
        });
    }

    public static void updateLabelColor(Label label, Boolp condition, Color enabled, Color disabled, float duration, Interp interpolation){
        updateLabelColor(label, condition.get(), enabled, disabled, duration, interpolation);
    }

    public static void updateLabelColor(Label label, boolean condition, Color enabled, Color disabled, float duration, Interp interpolation){
        Color col = condition ? enabled : disabled;
        // only send the go signal if actions array does not have a queued ColorAction and the current color != target color
        if(!deepSearch(label.getActions(), ColorAction.class) && !label.color.equals(col)){
            label.addAction(Actions.color(col, duration, interpolation));
        }
    }
}
