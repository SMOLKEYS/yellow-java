package yellow.util.variable;

import arc.func.*;

/** Delegates the getter/setter logic of a variable to an external, often persistent source, like {@code Core.settings}. */
public interface VariableDelegator<T>{

    T get();

    VariableDelegator<T> set(T value);

    default VariableDelegator<T> set(Func<T, T> prev){
        set(prev.get(get()));
        return this;
    }
}
