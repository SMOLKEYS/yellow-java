package yellow.entities.units;

import mindustry.type.*;
import mindustry.entities.units.*;

public class DisableableWeaponMount extends WeaponMount{
    /** whether or not the weapon is disabled */
    public boolean disabled = false;
    
    public DisableableWeaponMount(Weapon weapon){
        super(weapon);
    }
}
