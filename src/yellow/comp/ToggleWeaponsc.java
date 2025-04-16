package yellow.comp;

import arc.func.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import yellow.entities.units.*;

public interface ToggleWeaponsc extends Weaponsc{
    
    default void eachToggleMount(Cons<ToggleWeaponMount> cons){
        for(WeaponMount w : mounts()){
            if(w instanceof ToggleWeaponMount t) cons.get(t);
        }
    }
}
