package yellow.util;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.type.*;
import yellow.type.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class YellowUtils{
    
    public static boolean isEnabled(String modName){
        return settings.getBool("mod-" + modName + "-enabled");
    }
    
    public static void mirror(Weapon[] in, boolean nameable, boolean alternate, UnitType unit){
        for(int i = 0; i < in.length; i++){
            Weapon mog = in[i].copy();
            mog.x = in[i].x - (in[i].x * 2);
            if(alternate){
                mog.reload = in[i].reload * 2;
            };
            mog.name = in[i].name + "-m";
            mog.load();
            if(nameable){
                ((NameableWeapon) mog).displayName = ((NameableWeapon) in[i]).displayName + " (Inv)";
            };
            unit.weapons.add(mog);
        };
    }
}
