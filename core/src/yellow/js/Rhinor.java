package yellow.js;

import arc.util.*;
import arc.util.serialization.*;
import mindustry.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import rhino.*;
import yellow.util.*;

public class Rhinor{
    private static boolean loaded = false;
    private static ImporterTopLevel scope = null;

    private static int counter = 0;
    private static boolean counting = false;

    public static final String[]
            basicFilter = {"bundles", "texts", "shaders", "sprites", "META-INF"},
            empty = {};

    public static void track(){
        if(counting) return;
        counting = true;
        counter = 0;
    }

    public static int finishTrack(){
        if(!counting) return -1;
        counting = false;
        int cr = counter;
        counter = 0;
        return cr;
    }

    public static void putVariable(String name, Object obj){
        if(!loaded){
            scope = (ImporterTopLevel) Vars.mods.getScripts().scope;
            loaded = true;
        }

        scope.put(name, scope, obj);
    }

    public static void importPackage(String... packageNames){
        if(!loaded){
            scope = (ImporterTopLevel) Vars.mods.getScripts().scope;
            loaded = true;
        }

        for(var s: packageNames){
            NativeJavaPackage pkg = new NativeJavaPackage(s, Vars.mods.mainLoader());
            pkg.setParentScope(scope);
            scope.importPackage(pkg);
            if(counting) counter++;
        }
    }

    /** Imports all found classes from the given mod. */
    public static void importAllModPackages(LoadedMod mod, String[] filter, boolean filterAsWhitelist){
        if(!mod.enabled() || !mod.isJava()) return;
        track();
        FiUtils.walkAll(mod.root, e -> {
            if(!e.isDirectory()) return;

            String er = e.toString();
            String fstr = er.endsWith("/") ? er.replace('/', '.').substring(0, er.length() - 1) : er.replace('/', '.');

            for(String elem : filter){
                boolean filtered = fstr.startsWith(elem);

                if(filtered && filterAsWhitelist){
                    importPackage(fstr);
                }else if(!filtered && !filterAsWhitelist) importPackage(fstr);
            }
        });

        Log.debug("Imported @ packages from mod '@'", finishTrack(), mod.meta.name);
    }


    public static void importAllModPackages(Mod modClass, String[] filter, boolean filterAsWhitelist){
        importAllModPackages(Vars.mods.getMod(modClass.getClass()), filter, filterAsWhitelist);
    }

    /** Imports only the main mod classes from the given mod. */
    public static void importMainModPackages(LoadedMod mod){
        importAllModPackages(mod, new String[]{getRootPackage(mod)}, true);
    }

    public static void importMainModPackages(Mod modClass){
        importMainModPackages(Vars.mods.getMod(modClass.getClass()));
    }

    public static CompiledScript compileString(String script){
        return new CompiledScript(script, null, null);
    }

    /** Returns the root package of the given mod. */
    public static String getRootPackage(LoadedMod mod){
        if(!mod.enabled() || !mod.isJava()) return null;

        String meta;
        boolean hasJson = mod.root.child("mod.json").exists(),
                hasHjson = mod.root.child("mod.hjson").exists();

        if(hasJson && hasHjson){
            throw new IllegalStateException("Ambiguity error; both mod.json and mod.hjson exist. (How did you even do this?)");
        }else if(hasJson){
            meta = mod.root.child("mod.json").readString();
        }else if(hasHjson){
            meta = mod.root.child("mod.hjson").readString();
        }else{
            throw new IllegalStateException("No mod.json or mod.hjson found. (How did you even do this?)");
        }

        String main = Jval.read(meta).getString("main");

        return main.substring(0, main.indexOf('.'));
    }

    /** Imports all classes from all active Java mods. Don't do this. */
    public static void importAllMods(){
        Vars.mods.eachEnabled(Rhinor::importMainModPackages);
    }

    public static MetadataType getMetaType(LoadedMod mod){
        boolean hasJson = mod.root.child("mod.json").exists(),
                hasHjson = mod.root.child("mod.hjson").exists();

        if(hasJson && hasHjson){
            return MetadataType.ILLEGAL_MULTI;
        }else if(hasJson){
            return MetadataType.JSON;
        }else if(hasHjson){
            return MetadataType.HJSON;
        }else{
            return MetadataType.ILLEGAL_NIL;
        }
    }

    public enum MetadataType{
        JSON, HJSON, ILLEGAL_NIL, ILLEGAL_MULTI
    }

}
