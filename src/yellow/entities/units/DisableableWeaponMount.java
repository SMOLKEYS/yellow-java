package yellow.entities.units;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.units.WeaponMount;
import mindustry.type.Weapon;

public class DisableableWeaponMount extends WeaponMount{
    /** whether or not the weapon is enabled */
    public boolean enabled = true;
    
    public DisableableWeaponMount(Weapon weapon){
        super(weapon);
    }
    
    public void write(Writes write){
        write.bool(enabled);
    }
    
    public void read(Reads read){
        enabled = read.bool();
    }
}
