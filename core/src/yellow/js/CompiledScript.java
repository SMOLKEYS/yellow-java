package yellow.js;

import arc.func.*;
import arc.util.*;
import mindustry.*;
import rhino.*;

/** A compiled RhinoJS script snippet. Probably preferable over {@link mindustry.mod.Scripts#runConsole(String)} in some cases. */
public class CompiledScript implements Runnable{
    public final String script;
    public final Script compiledScript;
    public Prov<Context> contextProv = () -> Vars.mods.getScripts().context;
    public Prov<Scriptable> scopeProv = () -> Vars.mods.getScripts().scope;

    public CompiledScript(String script, @Nullable Prov<Context> contextProv, @Nullable Prov<Scriptable> scopeProv){
        this.script = script;
        if(contextProv != null) this.contextProv = contextProv;
        this.compiledScript = this.contextProv.get().compileString(script, Integer.toHexString(hashCode()) + "-compiled-script.js", 1);
        if(scopeProv != null) this.scopeProv = scopeProv;
    }

    public Object exec(){
        return compiledScript.exec(contextProv.get(), scopeProv.get());
    }

    @Override
    public void run(){
        exec();
    }
}
