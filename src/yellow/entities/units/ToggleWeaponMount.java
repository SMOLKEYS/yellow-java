package yellow.entities.units;

import arc.util.io.*;
import mindustry.entities.units.*;
import mindustry.type.*;
import yellow.type.weapons.*;

public class ToggleWeaponMount extends WeaponMount{
    private static final ToggleWeaponMount inst = new ToggleWeaponMount(new Weapon("do-not-touch-this"));

    public boolean enabled = true;

    public ToggleWeaponMount(Weapon weapon){
        super(weapon);
        if(weapon instanceof ToggleWeapon w) enabled = w.enabledDefault;
    }

    public void read(Reads read){
        enabled = read.bool();
    }

    public void write(Writes write){
        write.bool(enabled);
    }

    public static void throwaway(Reads read){
        inst.read(read);
    }
}
