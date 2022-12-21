package yellow.entities.units;

import arc.util.io.*;
import mindustry.entities.units.*;
import mindustry.type.*;
import yellow.type.*;

public class DisableableWeaponMount extends WeaponMount{
    /** whether or not the weapon is enabled */
    public boolean enabled = true;
    
    public DisableableWeaponMount(Weapon weapon){
        super(weapon);
        
        enabled = ((DisableableWeapon) weapon).enabledDefault;
    }


    //TODO
    public void write(Writes write){
        //write.bool(enabled);
    }
    
    public void read(Reads read){
        //try{
        //    enabled = read.bool();
        //}catch(Exception e){
        //    enabled = ((DisableableWeapon) weapon).enabledDefault;
        //}
    }
    
    /** Called when this weapon mount is enabled. */
    public void enabled(){
    }
    
    /** Called when this weapon mount is disabled. */
    public void disabled(){
    }
}
