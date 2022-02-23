package yellow.util;

import arc.*;
import mindustry.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class YellowUtils{
    
    public static void isEnabled(String modName){
        settings.getBool("mod-" + modName + "-enabled");
    }
}