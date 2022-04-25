package yellow.type;

import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.entities.units.*;

public class DisableableWeapon extends NameableWeapon{
    
    public DisableableWeapon(String iName, String dName){
        super(iName, dName);
        mountType = DisableableWeaponMount::new;
    }
    //basically, if the mount is disabled, ignore update and draw code entirely
    @Override
    public void update(Unit unit, WeaponMount mount){
        if(((DisableableWeaponMount) mount).disabled) return;
        super.update(unit, mount);
    }
    
    @Override
    public void draw(Unit unit, WeaponMount mount){
        if(((DisableableWeaponMount) mount).disabled) return;
        super.draw(unit, mount);
    }
}
