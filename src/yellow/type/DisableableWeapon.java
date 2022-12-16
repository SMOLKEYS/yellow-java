package yellow.type;

import mindustry.entities.units.*;
import mindustry.gen.*;
import yellow.entities.units.*;

public class DisableableWeapon extends NameableWeapon{
    
    public boolean mirroredVersion = false;
    
    public DisableableWeapon(String name, String displayName){
        super(name, displayName);
        mountType = DisableableWeaponMount::new;
    }
    //basically, if the mount is disabled, ignore update and draw code entirely
    //breaks with normal mirror implementation
    @Override
    public void update(Unit unit, WeaponMount mount){
        if(!((DisableableWeaponMount) mount).enabled) return;
        super.update(unit, mount);
    }
    
    @Override
    public void draw(Unit unit, WeaponMount mount){
        if(!((DisableableWeaponMount) mount).enabled) return;
        super.draw(unit, mount);
    }
}
