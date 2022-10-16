package yellow.internal

import arc.util.Log
import mindustry.Vars
import rhino.ImporterTopLevel
import rhino.NativeJavaPackage
import yellow.internal.util.YellowUtils.*

//sh1p you have done it
open class YellowClassGateway{

    fun load(){
        var scope = Vars.mods.scripts.scope as ImporterTopLevel
        
        val packages = Vars.tree.get("classpaths/yellow-classpath.txt").readString().split('\n')

        controlledLog("[yellow]--------STARTING GATEWAY--------[]")
        controlledLog("Scope: $scope, Generator: $this, Class Loader: ${Vars.mods.mainLoader()}")
        packages.forEach{
            val p = NativeJavaPackage(it, Vars.mods.mainLoader())
            controlledLog("importing classes from $it...")
            p.parentScope = scope
            scope.importPackage(p)
        }
        controlledLog("[green]--------GATEWAY STARTED!--------[]")
    }
}
