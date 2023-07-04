package yellow.internal

import mindustry.*
import rhino.*
import yellow.internal.util.YellowUtils.controlledLog

//sh1p you have done it
object YellowClassGateway{

    private var uniGateImports = 0
    private var uniGateErrors = 0

    @JvmStatic
    fun load(){
        val scope = Vars.mods.scripts.scope as ImporterTopLevel
        
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
