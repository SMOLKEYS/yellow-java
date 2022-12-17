package yellow.type;

import arc.*;
import arc.math.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.type.*;
import yellow.entities.units.*;
import kotlin.ranges.*;

import static yellow.internal.util.YellowUtils.*;


public class DistanceBasedWeapon extends DisableableWeapon{
    
    public int distance = 8 * 15;
    
    public DistanceBasedWeapon(String name, String displayName){
        super(name, displayName);
        alwaysContinuous = true;
    }
    
    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);
        
        boolean shooter = unit.isPlayer() && (Mathf.round(Mathf.dst(unit.x, unit.y, Core.camera.position.x, Core.camera.position.y)) >= distance) && ((DisableableWeaponMount) mount).enabled;
        
        if(mount.bullet != null){
            mount.bullet.keepAlive = shooter;
        }else{
            if(shooter) shoot(unit, mount, unit.x, unit.y, unit.rotation - 90f + baseRotation);
        }
        
        internalLog(shooter);
    }
}
