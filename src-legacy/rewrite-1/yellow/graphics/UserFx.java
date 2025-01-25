package yellow.graphics;

import arc.func.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;

/** Special graphical effects applied to Yellow and the surrounding world for specific players. */
@SuppressWarnings("UnusedReturnValue")
public class UserFx{

    public static ObjectMap<String, FxPair<Unit>> entries = new ObjectMap<>();

    public static FxPair<Unit> defaultEntry = new FxPair<>();

    /** Returns the draw entry for the specified name. If null, assumes the name of the player, colors removed.
     * If the entry does not exist or the player name is also null, returns the default draw entry. */
    public static FxPair<Unit> getEntry(@Nullable String name){
        String fName = name != null ? name : Vars.player.name;
        if(name == null) return defaultEntry; //edge case

        FxPair<Unit> entry = entries.get(Strings.stripColors(name));
        if(entry == null) return defaultEntry;
        return entry;
    }

    /** Adds an entry for the specified name. */
    public static void addEntry(String name, FxPair<Unit> draw){
        if(entries.containsKey(name)) return;
        entries.put(name, draw);
    }

    /** Adds multiple entries sharing one draw call. To be used for users with various names. */
    public static void addEntries(FxPair<Unit> draw, String... entries){
        for(String s: entries){
            addEntry(s, draw);
        }
    }

    public static void load(){

    }

    public static class FxPair<T>{
        public Cons<T> target = e -> {};
        public Runnable global = () -> {};
    }
}
