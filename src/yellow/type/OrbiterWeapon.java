package yellow.type:

import arc.util.*;
import arc.math.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;

public class OrbiterWeapon extends DisableableWeapon{
    
    public float orbitRange = 8*10f, orbitSpeed = 1f;
    
    public OrbiterWeapon(String name){
        super(name);
        invisible = true;
    }
    
    @Override
    public void update(Unit unit, WeaponMount mount){
        Angles.circleVectors(1, orbitRange, Time.time * orbitSpeed, (wx, wy) -> {
            mount.x = unit.x + x + wx;
            mount.y = unit.y + y + wy;
        });
    }
}