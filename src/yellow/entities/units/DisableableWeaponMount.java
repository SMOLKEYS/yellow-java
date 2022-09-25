package yellow.entities.units;

import mindustry.type.*;
import mindustry.entities.units.*;

public class DisableableWeaponMount extends WeaponMount{
    /** whether or not the weapon is enabled */
    public boolean enabled = true;
    
    public DisableableWeaponMount(Weapon weapon){
        super(weapon);
    }
}
