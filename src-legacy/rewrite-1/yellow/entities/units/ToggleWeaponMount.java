package yellow.entities.units;

import arc.util.io.*;
import mindustry.entities.units.*;
import mindustry.type.*;
import yellow.comp.*;
import yellow.type.weapons.*;

public class ToggleWeaponMount extends WeaponMount implements Savec{
    public boolean enabled = true;

    public ToggleWeaponMount(Weapon weapon){
        super(weapon);
        if(weapon instanceof ToggleWeapon w) enabled = w.enabledDefault;
    }

    @Override
    public void read(Reads read){
        enabled = read.bool();
    }

    @Override
    public void write(Writes write){
        write.bool(enabled);
    }
}
