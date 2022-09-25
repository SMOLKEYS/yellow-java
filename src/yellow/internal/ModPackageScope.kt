package yellow.internal

import arc.struct.*
import rhino.*
import mindustry.Vars
import mindustry.game.EventType.*

//sh1p you have done it
open class ModPackageScope{

    fun load(){
        var scope = Vars.mods.scripts.scope as ImporterTopLevel
        
        val packages = arrayOf(
            "yellow",
            "yellow.content",
            "yellow.weapons",
            "yellow.ctype",
            "yellow.internal",
            "yellow.internal.util",
            "yellow.type",
            "yellow.game",
            "yellow.world",
            "yellow.world.blocks",
            "yellow.world.blocks.units",
            "yellow.ui",
            "yellow.ui.buttons",
            "yellow.ui.buttons.dialogs",
            "yellow.entities",
            "yellow.entities.bullet",
            "yellow.entities.units",
            "yellow.entities.units.entity",
        )
        
        packages.forEach{
            var p = NativeJavaPackage(it, Vars.mods.mainLoader())
            p.setParentScope(scope)
            scope.importPackage(p)
        }
    }
}
