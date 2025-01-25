package yellow.entities.units;

import arc.util.io.*;
import mindustry.entities.units.*;
import mindustry.type.*;
import yellow.internal.*;
import yellow.type.*;

import static yellow.util.YellowUtils.*;

public class DisableableWeaponMount extends WeaponMount implements Savec{
    /** whether the weapon is enabled */
    public boolean enabled;
    
    public DisableableWeaponMount(Weapon weapon){
        super(weapon);
        
        enabled = ((DisableableWeapon) weapon).enabledDefault;
    }

    @Override
    public void write(Writes write){
        internalLog("begin write (@) for @", enabled, weapon);
        write.bool(enabled);
        internalLog("write complete");
    }

    @Override
    public void read(Reads read){
        boolean b = read.bool();
        internalLog("begin read (@) for @", b, weapon);
        enabled = b;
        internalLog("read complete");
    }
    
    /** Called when this weapon mount is enabled. */
    public void enabled(){
    }
    
    /** Called when this weapon mount is disabled. */
    public void disabled(){
    }
}
