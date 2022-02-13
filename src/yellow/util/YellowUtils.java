package yellow.util;

import arc.*;

import static arc.Core.*;

public class YellowUtils{
    
    public void isEnabled(String modName){
        settings.get("mod-" + modName + "-enabled", "");
    }
}