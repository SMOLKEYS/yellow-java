package yellow.ui

import arc.*
import arc.files.*
import com.github.mnemotechnician.mkui.extensions.dsl.*
import mindustry.*
import mindustry.Vars.*
import mindustry.gen.*
import yellow.*
import yellow.internal.util.*

object YellowSettings{

    val tmpDir: Fi = Core.settings.dataDirectory.child("yellow.jar")

    @JvmStatic
    fun load(){
        ui.settings.addCategory("Yellow (Java)", Icon.right){ table ->

            table.checkPref("Verbose Logging", false){
                YellowPermVars.verboseLoggering = it
            }
            
            table.checkPref("Internal Logging", false){
                YellowPermVars.internalLoggering = it
            }
            
            table.checkPref("Disable Allied Yellow Unit Weapons On World Reload", true){
                YellowPermVars.weaponSanityCheck = it
            }
            
            table.textPref("Source BE Repo", YellowPermVars.sourceBERepo){
                if(it.isBlank()){
                    YellowPermVars.sourceBERepo = "https://github.com/SMOLKEYS/yellow-java-builds/releases/latest/download/yellow-java.jar"
                }else{
                    YellowPermVars.sourceBERepo = it
                }
            }
            
            table.textPref("Source Repo", YellowPermVars.sourceReleaseRepo){
                if(it.isBlank()){
                    YellowPermVars.sourceReleaseRepo = "https://github.com/SMOLKEYS/yellow-java/releases/latest/download/yellow-java.jar"
                }else{
                    YellowPermVars.sourceReleaseRepo = it
                }
            }

            table.row()

            table.textButton("Update\n(Do not spam!)", wrap = false){
                YellowUtils.getAndWrite(YellowPermVars.sourceBERepo, tmpDir, true){
                    Vars.mods.importMod(it)
                    it.delete()
                    ui.showInfoOnHidden("Mod updated. Restart the game."){ Core.app.exit() }
                }
            }

            table.row()

            table.textButton("Check Status", wrap = false){
                YellowUtils.getWorkflowStatus()
            }
        }
    }
}
