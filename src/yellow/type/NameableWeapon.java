package yellow.type;

import mindustry.type.*;

    /**
     * A weapon with a configurable display name.
     */
public class NameableWeapon extends Weapon{
    public String displayName = "no config name";
    
    public NameableWeapon(String internalName, String dName){
        super(internalName);
        this.displayName = dName;
    }
}
