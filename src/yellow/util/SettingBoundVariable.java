package yellow.util;

import arc.*;
import arc.func.*;

public class SettingBoundVariable<T>{
    private final String name;
    private final T def;

    public SettingBoundVariable(String name, T def){
        this(name, def, false);
    }

    public SettingBoundVariable(String name, T def, boolean applyDefs){
        this.name = name;
        this.def = def;

        if(!(def instanceof Float || def instanceof Integer || def instanceof Boolean || def instanceof Long
                || def instanceof String || def instanceof byte[]))
            throw new IllegalArgumentException("Invalid object for setting bound variable");

        if(applyDefs && !Core.settings.has(name)) Core.settings.put(name, def);
    }

    public T get(){
        return SafeSettings.get(name, def);
    }

    public T get(T altDef){
        return SafeSettings.get(name, altDef);
    }

    public SettingBoundVariable<T> set(T object){
        Core.settings.put(name, object);
        return this;
    }

    public SettingBoundVariable<T> set(Func<T, T> prev){
        return set(prev.get(get()));
    }
}
