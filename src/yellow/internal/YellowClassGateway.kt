package yellow.internal

import arc.Core
import arc.util.Log
import mindustry.Vars
import mindustry.world.meta.StatCat.function
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
    
    fun loadUniversal(){
        var scope = Vars.mods.scripts.scope as ImporterTopLevel
        
        val source = Core.settings.dataDirectory.child("yellow").child("universal-classpath.txt")
        
        if(!source.exists()) source.writeString("put.mod.paths.or.game.paths.here")
        
        val packages = source.readString().split('\n')
        
        controlledLog("[yellow]--------STARTING UNIVERSAL GATEWAY--------[]")
        packages.forEach{
            val p = NativeJavaPackage(it, Vars.mods.mainLoader())
            controlledLog("importing classes from $it...")
            p.parentScope = scope
            scope.importPackage(p)
        }
        controlledLog("[green]--------UNIVERSAL GATEWAY STARTED!--------[]")
    }
}
