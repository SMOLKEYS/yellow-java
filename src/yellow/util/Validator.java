package yellow.util;

import arc.util.*;
import mindustry.gen.*;
import yellow.entities.units.*;
import yellow.gen.*;

public class Validator{

    public static void notNull(Object param){
        if(param == null) throw new RuntimeException("what the FUCK are you doing");
    }

    public static boolean hasToggleWeapons(Weaponsc entity){
        return entity != null && (entity instanceof WeaponMasterUnit || Structs.contains(entity.mounts(), e -> e instanceof ToggleWeaponMount));
    }
}
