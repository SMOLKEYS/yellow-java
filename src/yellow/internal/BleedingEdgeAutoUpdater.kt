package yellow.internal

import mindustry.*
import yellow.*
import yellow.ui.*
import yellow.internal.util.*

object BleedingEdgeAutoUpdater{
    val vtype = if(Yellow.getSelf().meta.version.contains(".")) "release" else "bleeding-edge"
    
    fun start(){
        when(vtype){
            "release" -> {}
            "bleeding-edge" -> {
                if(YellowUtilsKt.getBleedingEdgeVersion() < Yellow.getSelf().meta.version.toInt()){
                    Vars.ui.showConfirm("Update", "Found a new bleeding edge version for Yellow.\nUpdate now?"){
                        YellowUtils.getAndWrite(YellowPermVars.sourceRepo, YellowSettings.tmpDir, true){
                            Vars.mods.importMod(it)
                            it.delete()
                            Vars.ui.showInfoOnHidden("Mod updated. Restart the game."){ Core.app.exit() }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
