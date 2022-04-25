package yellow.util;

import arc.*;
import mindustry.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class YellowUtils{
    
    public static boolean isEnabled(String modName){
        return settings.getBool("mod-" + modName + "-enabled");
    }
}
