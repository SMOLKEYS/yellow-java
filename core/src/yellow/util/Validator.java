package yellow.util;

import arc.util.*;
import mindustry.gen.*;
import yellow.entities.units.*;
import yellow.gen.*;

public class Validator{
    public static boolean hasToggleWeapons(Weaponsc entity){
        return entity != null && (entity instanceof WeaponMasterUnit || Structs.contains(entity.mounts(), e -> e instanceof ToggleWeaponMount));
    }
}
