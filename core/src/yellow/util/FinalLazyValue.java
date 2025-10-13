package yellow.util;

import arc.func.*;

public class FinalLazyValue<T>{
    private Prov<T> tProv;
    private T t;

    public FinalLazyValue(){

    }

    public FinalLazyValue(Prov<T> prov){
        tProv = prov;
    }

    public void set(Prov<T> inp){
        if(tProv != null) return;
        tProv = inp;
    }

    public T get(){
        return t != null ? t : (t = tProv.get());
    }
}
