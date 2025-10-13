package yellow.type.weapons;

import arc.func.*;
import arc.struct.*;
import mindustry.type.*;

/** Utility class for handling special mirror properties. */
public class Mirror{
    private static final ObjectMap<String, ToggleWeapon> mirrors = new ObjectMap<>();

    public static void apply(Seq<Weapon> target, ToggleWeapon... weapons){
        for(ToggleWeapon t: weapons){
            if(t == null) return; //ignore null entries

            if(t.willMirror){
                target.add(t, Mirror.reflect(t, t.properties));
            }else{
                target.add(t);
            }
        }
    }

    public static ToggleWeapon reflect(ToggleWeapon source, ReflectProperty... properties){
        if(mirrors.containsKey(source.name)) return mirrors.get(source.name);
        var t = source.copy();

        t.name = source.name + "-mirror";
        t.displayName = source.displayName + " (Mirror)";
        t.x = -source.x;
        t.display = false;

        source.mirrored = t;
        t.original = source;

        for(var s: properties){
            s.get(source, t);
        }

        mirrors.put(source.name, t);
        return t;
    }

    public static ToggleWeapon get(ToggleWeapon original){
        return get(original.name);
    }

    public static ToggleWeapon get(String original){
        return mirrors.get(original);
    }


    public enum ReflectProperty{
        flipY((s, t) -> {
            t.y = -s.y;
        }),
        doubledReload((s, t) -> {
            t.reload = s.reload * 2;
        });

        private final Cons2<ToggleWeapon, ToggleWeapon> cons;

        ReflectProperty(Cons2<ToggleWeapon, ToggleWeapon> cons){
            this.cons = cons;
        }

        public void get(ToggleWeapon source, ToggleWeapon target){
            cons.get(source, target);
        }
    }
}
