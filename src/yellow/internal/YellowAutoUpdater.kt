package yellow.internal

import arc.*
import arc.util.*
import arc.util.serialization.*
import com.github.smol.kotmindy.mindustry.ui.*
import mindustry.*
import yellow.*
import yellow.ui.*
import yellow.internal.util.YellowUtils.*

object YellowAutoUpdater{
    val vtype = if(Yellow.getSelf().meta.version.contains(".")) "release" else "bleeding-edge"
    val jsr = JsonReader()

    @JvmStatic
    fun start(){
        when(vtype){
            "release" -> {
                Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java/releases", {
                    val res = it.resultAsString

                    try{
                        val version = jsr.parse(res)[0]["tag_name"].asString().replace("v", "")
                        val kr = if(version.toFloatOrNull() != null) version.toFloat() else 0f
                        val rk = Yellow.getSelf().meta.version.replace("v", "")
                        val krk = if(rk.toFloatOrNull() != null) rk.toFloat() else 99999.99f

                        controlledLog(version)
                        controlledLog("$kr")

                        if(kr > krk){
                            showTitledConfirm("New yellow-java release!", "Update now?"){
                                getAndWrite(YellowPermVars.sourceReleaseRepo, YellowSettings.tmpDir, true){
                                    Vars.mods.importMod(it)
                                    it.delete()
                                    showInfo("Mod imported. The game will now restart."){ Core.app.exit() }
                                }
                            }
                        }
                    }catch(e: Exception){
                        Log.err(e)
                    }
                }, {
                    Core.app.post{
                        it.printStackTrace()
                        Log.err(it)
                    }
                })
            }
            "bleeding-edge" -> {
                Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java-builds/releases", {
                    val res = it.resultAsString
            
                    try{
                        val version = jsr.parse(res)[0]["tag_name"].asString()
                        val kr = if(version.toIntOrNull() != null) version.toInt() else 0 //anything can happen.
                        val rk = Yellow.getSelf().meta.version
                        val krk = if(rk.toIntOrNull() != null) rk.toInt() else 999999
                        
                        controlledLog(version)
                        controlledLog("$kr")
                        
                        if(kr > krk) showTitledConfirm("Update", "Found a new bleeding edge version for Yellow.\nUpdate now? (current: $krk, new: $kr)"){
                            getAndWrite(YellowPermVars.sourceBERepo, YellowSettings.tmpDir, true){
                                Vars.mods.importMod(it)
                                it.delete()
                                showInfo("Mod updated. Restart the game."){ Core.app.exit() }
                            }
                        }
                    }catch(e: Exception){
                        Log.err(e)
                    }
                }, {
                    Core.app.post{
                        it.printStackTrace()
                        Log.err(it)
                    }
                })
            }
            else -> {}
        }
    }
}
