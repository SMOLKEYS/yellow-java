package yellow.util;

import arc.func.*;
import arc.struct.*;
import mindustry.gen.*;

public class Deathcatcher<T extends Healthc>{
    private final Seq<T> killed = new Seq<>(256);

    public boolean add(T entity){
        return killed.addUnique(entity);
    }

    public boolean remove(T entity){
        return killed.remove(entity);
    }

    public void clear(){
        killed.clear();
    }

    public void update(){
        killed.remove(ent -> !ent.dead());
    }

    public void revive(Cons<T> extra){
        killed.each(e -> {
            e.add();
            extra.get(e);
        });
    }
}
