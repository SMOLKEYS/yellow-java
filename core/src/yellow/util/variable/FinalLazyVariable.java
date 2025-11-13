package yellow.util.variable;

import arc.func.*;

public class FinalLazyVariable<T> implements VariableDelegator<T>{
    private Prov<T> tProv;
    private T t;

    public FinalLazyVariable(){

    }

    public FinalLazyVariable(Prov<T> prov){
        tProv = prov;
    }

    /** Write once, write never.*/
    @Override
    public FinalLazyVariable<T> set(T value){
        if(t != null) return this;
        t = value;
        return this;
    }

    @Override
    public T get(){
        return t != null ? t : (t = tProv.get());
    }
}
