package yellow.type;

import arc.*;
import mindustry.type.*;
import yellow.internal.*;

public class NameableWeapon extends Weapon implements Namec, YContent {
    /** The weapon display name, as shown in UI. */
    public String displayName;
    /** The weapon description. Defaults to the weapon description found in bundles. */
    public String description;
    
    public NameableWeapon(String name){
        super(name);

        this.displayName = nameLocalized();
        this.description = descriptionLocalized();

        YellowContentLoader.nameableWeapons.add(this);
        YellowContentLoader.all.add(this);
    }

    @Override
    public String nameLocalized(){
        displayName = Core.bundle.get("weapon." + name + ".name");
        return displayName;
    }

    @Override
    public String descriptionLocalized(){
        return Core.bundle.get("weapon." + name + ".description");
    }

}
