package yellow.internal

import arc.struct.*
import rhino.*
import mindustry.Vars
import mindustry.game.EventType.*

//sh1p you have done it
open class YellowClassGateway{

    fun load(){
        var scope = Vars.mods.scripts.scope as ImporterTopLevel
        
        val packages = Vars.tree.get("text/yellow-classpath.txt").readString().split('\n')
        
        packages.forEach{
            var p = NativeJavaPackage(it, Vars.mods.mainLoader())
            p.setParentScope(scope)
            scope.importPackage(p)
        }
    }
}
