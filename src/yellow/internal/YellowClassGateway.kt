package yellow.internal

import arc.util.Log
import mindustry.Vars
import rhino.ImporterTopLevel
import rhino.NativeJavaPackage

//sh1p you have done it
open class YellowClassGateway{

    fun load(){
        var scope = Vars.mods.scripts.scope as ImporterTopLevel
        
        val packages = Vars.tree.get("classpaths/yellow-classpath.txt").readString().split('\n')

        Log.info("[yellow]--------STARTING GATEWAY--------[]")
        Log.info("Scope: $scope, Generator: $this")
        packages.forEach{
            var p = NativeJavaPackage(it, Vars.mods.mainLoader())
            Log.info("importing classes from $p...")
            p.parentScope = scope
            scope.importPackage(p)
            Log.info("success!")
        }
        Log.info("[green]--------GATEWAY STARTED!--------[]")
    }
}
