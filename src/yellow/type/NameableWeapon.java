package yellow.type;

import mindustry.type.Weapon;

public class NameableWeapon extends Weapon{
    /** The weapon display name, as shown in UI. */
    public String displayName = "displayed weapon name";
    /** The weapon description. */
    public String description;
    
    public NameableWeapon(String name, String displayName){
        super(name);
        if(displayName == null) throw new NullPointerException("displayName of weapon " + name + " cannot be null.");
        this.displayName = displayName;
    }
}
