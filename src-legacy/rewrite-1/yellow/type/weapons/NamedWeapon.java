package yellow.type.weapons;

import arc.*;
import arc.scene.ui.layout.*;
import mindustry.type.*;

/** A weapon with a display name and description. */
public class NamedWeapon extends Weapon{
    public String displayName, description;

    public NamedWeapon(String name){
        super(name);
        displayName = Core.bundle.get("weapon." + name + ".name");
        description = Core.bundle.get("weapon." + name + ".description");
    }

    @Override
    public void addStats(UnitType u, Table t){
        t.row();
        t.add("[lightgray]" + YellowStats.name.localized() + ": [accent]" + displayName + "[]");
        super.addStats(u, t);
    }

    public NamedWeapon copy(){
        try{
            return (NamedWeapon) clone();
        }catch(CloneNotSupportedException vile){
            throw new RuntimeException("god", vile);
        }
    }
}
