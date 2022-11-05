package yellow.type;

import arc.*;
import mindustry.type.*;

public class NameableWeapon extends Weapon{
    /** The weapon display name, as shown in UI. */
    public String displayName = "displayed weapon name";
    /** The weapon description. Defaults to the weapon description found in bundles. */
    public String description;
    
    public NameableWeapon(String name, String displayName){
        super(name);
        if(displayName == null) throw new NullPointerException("displayName of weapon " + name + " cannot be null.");
        this.displayName = displayName;
        
        if(this.description == null) this.description = descriptionLocalized();
    }
    
    public String descriptionLocalized(){
        return Core.bundle.get("weapon." + name + ".description");
    }
}
