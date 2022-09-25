package yellow.internal

import arc.struct.*
import rhino.*
import mindustry.Vars
import mindustry.game.EventType.*

//sh1p you have done it
open class ModPackageScope{

    fun load(){
        var scope = Vars.mods.scripts.scope as ImporterTopLevel
        
        val packages = arrayOf("yellow.content", "yellow.weapons")
        
        packages.forEach{
            var p = NativeJavaPackage(it, Vars.mods.mainLoader())
            p.setParentScope(scope)
            scope.importPackage(p)
        }
    }
}
