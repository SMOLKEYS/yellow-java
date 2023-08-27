package yellow.entities.units;

import arc.util.*;
import arc.util.io.*;
import mindustry.entities.units.*;
import mindustry.type.*;
import yellow.type.*;

import static yellow.internal.util.YellowUtils.*;

public class DisableableWeaponMount extends WeaponMount{
    /** whether or not the weapon is enabled */
    public boolean enabled = true;
    
    public DisableableWeaponMount(Weapon weapon){
        super(weapon);
        
        enabled = ((DisableableWeapon) weapon).enabledDefault;
    }


    //TODO
    public void write(Writes write){
        internalLog("begin write for " + weapon);
        write.bool(enabled);
        internalLog("write complete");
    }
    
    public void read(Reads read){
        try{
            internalLog("begin read for " + weapon);
            enabled = read.bool();
            internalLog("read complete");
        }catch(RuntimeException e){
            enabled = ((DisableableWeapon) weapon).enabledDefault;
            Log.err("Weapon state read error (" + weapon + ")", e);
        }
    }
    
    /** Called when this weapon mount is enabled. */
    public void enabled(){
    }
    
    /** Called when this weapon mount is disabled. */
    public void disabled(){
    }
}
