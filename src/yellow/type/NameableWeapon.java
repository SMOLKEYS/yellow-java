package yellow.type;

import arc.*;
import mindustry.type.*;

public class NameableWeapon extends Weapon{
    /** The weapon display name, as shown in UI. */
    public String displayName = nameLocalized();
    /** The weapon description. Defaults to the weapon description found in bundles. */
    public String description;
    
    public NameableWeapon(String name){
        super(name);
        
        this.description = descriptionLocalized();
    }
    
    public String descriptionLocalized(){
        return Core.bundle.get("weapon." + name + ".description");
    }

    public String nameLocalized(){
        displayName = Core.bundle.get("weapon." + name + ".name");
        return displayName;
    }
}
