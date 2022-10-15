package yellow.type;

import mindustry.type.Weapon;

    /**
     * A weapon with a configurable display name.
     */
public class NameableWeapon extends Weapon{
    public String displayName;
    
    public NameableWeapon(String name, String displayName){
        super(name);
        this.displayName = displayName;
    }
}
