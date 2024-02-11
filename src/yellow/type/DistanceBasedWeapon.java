package yellow.type;

import arc.*;
import arc.input.*;
import arc.math.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import yellow.entities.units.*;
import yellow.entities.units.entity.*;


public class DistanceBasedWeapon extends DisableableWeapon{
    
    public int distance = 8 * 22;
    public float holdTime = 0f;
    
    public DistanceBasedWeapon(String name){
        super(name);
        alwaysContinuous = true;
        mountType = DistanceBasedWeaponMount::new;
    }
    
    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);

        if(unit instanceof YellowUnitEntity){
            DistanceBasedWeaponMount mount1 = (DistanceBasedWeaponMount) mount;

            boolean shooter = unit.isPlayer() && (Mathf.round(Mathf.dst(unit.x, unit.y, Core.camera.position.x, Core.camera.position.y)) >= distance) && mount1.enabled;

            if(shooter) {
                mount1.shootTime++;
            } else {
                mount1.shootTime = 0f;
            }

            if(mount1.bullet != null && mount1.shootTime >= holdTime && !Core.input.keyDown(KeyCode.shiftLeft)) {
                mount1.bullet.keepAlive = shooter;
                if(shooter) mount1.bullet.time = 8f;
            } else {
                if(shooter && mount1.shootTime >= holdTime) {
                    shoot(unit, mount1, unit.x, unit.y, unit.rotation - 90f + baseRotation);
                }
            }
        }
    }
}
