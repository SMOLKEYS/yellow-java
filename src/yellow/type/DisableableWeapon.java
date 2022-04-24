package yellow.type;

import mindustry.gen.*;
import mindustry.entities.units.*;
import yellow.entities.units.*;

public class DisableableWeapon extends NameableWeapon{
    
    public DisableableWeapon(){
        super("", "no config name");
        mountType = DisableableWeaponMount::new;
    }
}
