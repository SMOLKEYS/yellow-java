package yellow.type;

import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.entities.units.*;

public class DisableableWeapon extends NameableWeapon{
    
    public DisableableWeapon(){
        super("");
        displayName = "unnamed disableable weapon";
        mountType = DisableableWeaponMount::new;
    }
}
