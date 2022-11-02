package yellow.type;

import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import yellow.entities.units.DisableableWeaponMount;

public class DisableableWeapon extends NameableWeapon{
    
    public boolean mirroredVersion = false;
    
    public DisableableWeapon(String name, String displayName){
        super(name, displayName);
        mountType = DisableableWeaponMount::new;
    }
    //basically, if the mount is disabled, ignore update and draw code entirely
    /**
     * TODO make the damn thing not break with mirrored weapons
     */
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
