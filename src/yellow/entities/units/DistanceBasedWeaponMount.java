package yellow.entities.units;

import mindustry.type.*;

public class DistanceBasedWeaponMount extends DisableableWeaponMount{
    public float shootTime = 0f;

    public DistanceBasedWeaponMount(Weapon weapon){
        super(weapon);
    }
}
