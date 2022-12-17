package yellow.type;

import arc.math.*;
import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.type.*;
import yellow.entities.units.*;
import kotlin.ranges.*;

public class SpeedWeapon extends DisableableWeapon{
    
    public IntRange speedRange;
    
    public SpeedWeapon(String name, String displayName, IntRange range){
        super(name, displayName);
        speedRange = range;
        alwaysShooting = true;
        alwaysContinuous = true;
    }
    
    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);
        
        mount.shoot = speedRange.contains(Mathf.round(unit.vel.len())) && ((DisableableWeaponMount) mount).enabled;
    }
}
