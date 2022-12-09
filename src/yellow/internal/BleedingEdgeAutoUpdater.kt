package yellow.internal

import arc.*
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
                Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java-builds/releases", {
                    val res = it.getResultAsString()
            
                    try{
                        val version = jsr.parse(res)[0]["name"].asString()
                        val kr = if(version != null && version.toIntOrNull() != null) version.toInt() else 0 //anything can happen.
                        
                        if(kr > Yellow.getSelf().meta.version.toInt()) Vars.ui.showConfirm("Update", "Found a new bleeding edge version for Yellow.\nUpdate now?"){
                            YellowUtils.getAndWrite(YellowPermVars.sourceRepo, YellowSettings.tmpDir, true){
                                Vars.mods.importMod(it)
                                it.delete()
                                Vars.ui.showInfoOnHidden("Mod updated. Restart the game."){ Core.app.exit() }
                            }
                        }
                    }catch(e: Exception){
                        Vars.ui.showException("Bleeding Edge Version GET Error", e)
                    }
                }, {
                    Core.app.post{
                        it.printStackTrace()
                        Vars.ui.showException("Bleeding Edge Version GET Error", it)
                    }
                })
            }
            else -> {}
        }
    }
}
