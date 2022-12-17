package yellow.type;

import arc.*;
import arc.math.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.type.*;
import yellow.entities.units.*;
import kotlin.ranges.*;

public class DistanceBasedWeapon extends DisableableWeapon{
    
    public int distance = 8 * 15;
    
    public DistanceBasedWeapon(String name, String displayName){
        super(name, displayName);
        alwaysContinuous = true;
    }
    
    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);
        
        mount.shoot = unit.isPlayer() && (Mathf.round(Mathf.dst(unit.x, unit.y, Core.camera.position.x, Core.camera.position.y)) >= distance) && ((DisableableWeaponMount) mount).enabled;
    }
}
