package yellow.entities.units;

import mindustry.entities.units.WeaponMount;
import mindustry.type.Weapon;

public class DisableableWeaponMount extends WeaponMount{
    /** whether or not the weapon is enabled */
    public boolean enabled = true;
    
    public DisableableWeaponMount(Weapon weapon){
        super(weapon);
    }
}
