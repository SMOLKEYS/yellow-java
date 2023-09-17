package yellow.internal

import mindustry.Vars
import rhino.*
import yellow.internal.util.YellowUtils.internalLog

object YellowConsoleBind{

    private val classes = arrayOf("yellow.internal.YellowContent", "yellow.Yellow", "yellow.YellowPermVars", "yellow.YellowVars")

    //TODO uhh not working fsr
    @JvmStatic
    fun load(){
        internalLog("YELLOW: STARTING CONSOLE BIND")

        val consoleScope = Vars.mods.scripts.scope as ImporterTopLevel
        var current: NativeJavaPackage?

        classes.forEach {
            internalLog("cycling $it")
            current = NativeJavaPackage(it, Vars.mods.mainLoader())
            internalLog("$current, $consoleScope")

            current!!.parentScope = consoleScope

            consoleScope.importPackage(current)
            internalLog("cycled $it")
        }

        internalLog("YELLOW: CONSOLE BIND STARTED")
    }
}