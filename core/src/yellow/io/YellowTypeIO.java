package yellow.io;

import arc.util.io.*;
import mindustry.entities.units.*;
import yellow.entities.units.*;

public class YellowTypeIO{

    public static void writeToggleWeapons(WeaponMount[] mounts, Writes write){
        if(mounts.length == 0){
            write.i(0);
            return;
        }

        int ss = 0;
        for(var w: mounts) if(w instanceof ToggleWeaponMount) ss++;
        write.i(ss);

        for(var w: mounts) if(w instanceof ToggleWeaponMount s) s.write(write);
    }

    public static void readToggleWeapons(WeaponMount[] mounts, Reads read, boolean dumpIfNotEqual){
        if(mounts.length == 0){
            for(int i = 0; i < read.i(); i++){
                ToggleWeaponMount.throwaway(read);
            }
            return;
        }

        int oldMountLength = read.i(); //old amount of mounts
        short currentMountLength = 0; //current amount of mounts
        for(var w: mounts) if(w instanceof ToggleWeaponMount) currentMountLength++;

        if(oldMountLength == currentMountLength){
            for(var w: mounts) if(w instanceof ToggleWeaponMount s) s.read(read);
        }else{
            if(dumpIfNotEqual){
                for(int i = 0; i < oldMountLength; i++){
                    ToggleWeaponMount.throwaway(read);
                }
                return;
            }
            ToggleWeaponMount[] saveMounts = new ToggleWeaponMount[currentMountLength];
            int strayBytes = 0;

            //list down all current mounts
            for(var w: mounts){
                if(w instanceof ToggleWeaponMount s){
                    saveMounts[strayBytes++] = s;
                }
            }

            for(var w: saveMounts){
                w.read(read);
            }

            //dump any unused bytes
            for(int i = 0; i < currentMountLength - strayBytes; i++){
                ToggleWeaponMount.throwaway(read);
            }
        }
    }
}
