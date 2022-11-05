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

    fun load(){
        ui.settings.addCategory("Yellow (Java)", Icon.right){ table ->

            table.checkPref("Verbose Logging", false){
                YellowPermVars.verboseLoggering = it
            }

            table.textPref("Source Repo", YellowPermVars.sourceRepo){
                if(it.isBlank()){
                    YellowPermVars.sourceRepo = "https://github.com/SMOLKEYS/yellow-java-builds/releases/latest/download/yellow-java.jar"
                }else{
                    YellowPermVars.sourceRepo = it
                }
            }

            table.row()

            table.textButton("Update\n(Do not spam!)", wrap = false){
                YellowUtils.getAndWrite(YellowPermVars.sourceRepo, tmpDir, true){
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