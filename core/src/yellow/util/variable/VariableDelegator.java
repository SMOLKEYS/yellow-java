package yellow.util.variable;

import arc.func.*;

public interface VariableDelegator<T>{

    T get();

    VariableDelegator<T> set(T value);

    default VariableDelegator<T> set(Func<T, T> prev){
        set(prev.get(get()));
        return this;
    }
}
