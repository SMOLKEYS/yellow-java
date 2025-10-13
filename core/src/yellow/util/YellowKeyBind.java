package yellow.util;

import arc.*;
import arc.input.*;
import arc.util.*;

public class YellowKeyBind{

    public static KeyBind add(String name, KeyBind.KeybindValue def){
        if(!Core.bundle.has("keybind." + name + ".name")) Log.warn("No bundle entry found for keybind @", name);
        return KeyBind.add(name, def, "yellow");
    }

    public static KeyBind add(String name, KeyBind.KeybindValue def, String subcategory){
        if(!Core.bundle.has("keybind." + name + ".name")) Log.warn("No bundle entry found for keybind @", name);
        if(!Core.bundle.has("category.yellow_" + subcategory + ".name")) Log.warn("No bundle entry found for keybind subcategory yellow_@", subcategory);
        return KeyBind.add(name, def, "yellow_" + subcategory);
    }
}
