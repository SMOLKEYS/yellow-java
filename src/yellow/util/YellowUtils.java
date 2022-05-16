package yellow.util;

import arc.*;
import mindustry.*;
import mindustry.type.*;
import yellow.type.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class YellowUtils{
    
    public static boolean isEnabled(String modName){
        return settings.getBool("mod-" + modName + "-enabled");
    }
    
    public static Weapon mirror(Weapon in, boolean nameable){
        Weapon mog = in.copy();
        mog.x = in.x - (in.x * 2);
        mog.reload = in.reload * 2;
        mog.name = in.name + "-m";
        if(nameable){
            ((NameableWeapon) mog).displayName = ((NameableWeapon) in).displayName + " (Inv)";
            return mog;
        } else {
            return mog;
        };
    }
}
