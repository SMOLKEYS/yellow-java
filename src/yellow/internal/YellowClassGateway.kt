package yellow.internal

import arc.Core
import arc.files.Fi
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
        Log.info("Scope: $scope, Generator: $this, Class Loader: ${Vars.mods.mainLoader()}")
        packages.forEach{
            val p = NativeJavaPackage(it, Vars.mods.mainLoader())
            Log.info("importing classes from $it...")
            p.parentScope = scope
            scope.importPackage(p)
        }
        Log.info("[green]--------GATEWAY STARTED!--------[]")
    }
}
