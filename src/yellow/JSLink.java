package yellow;

import mindustry.*;
import rhino.*;

public class JSLink{
    static boolean loaded = false;
    static ImporterTopLevel scope = null;

    public static void importPackage(String... packageNames){
        if(!loaded){
            scope = (ImporterTopLevel) Vars.mods.getScripts().scope;
        }

        for(var s: packageNames){
            NativeJavaPackage pkg = new NativeJavaPackage(s, Vars.mods.mainLoader());
            pkg.setParentScope(scope);
            scope.importPackage(pkg);
        }
    }
}
