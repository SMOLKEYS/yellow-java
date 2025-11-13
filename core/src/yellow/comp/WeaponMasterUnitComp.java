package yellow.comp;

import arc.func.*;
import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import yellow.entities.units.*;
import yellow.gen.*;
import yellow.io.*;

@EntityComponent
@EntityDef({WeaponMasterUnitc.class, Unitc.class})
abstract class WeaponMasterUnitComp implements Unitc{
    @Import WeaponMount[] mounts;
    
    public void eachToggleMount(Cons<ToggleWeaponMount> cons){
        for(WeaponMount w : mounts){
            if(w instanceof ToggleWeaponMount t) cons.get(t);
        }
    }

    @Override
    @MethodPriority(69) //man
    public void read(Reads read){
        YellowTypeIO.readToggleWeapons(mounts, read, false);
    }

    @Override
    @MethodPriority(69) //uh huh
    public void write(Writes write){
        YellowTypeIO.writeToggleWeapons(mounts, write);
    }
}
