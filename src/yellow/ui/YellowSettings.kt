package yellow.ui

import arc.Core
import arc.files.Fi
import com.github.mnemotechnician.mkui.extensions.dsl.textButton
import mindustry.Vars
import mindustry.Vars.ui
import mindustry.gen.Icon
import yellow.game.YellowPermVars
import yellow.internal.util.YellowUtils

object YellowSettings{

    val tmpDir: Fi = Core.settings.dataDirectory.child("yellow.jar")

    fun load(){
        Vars.ui.settings.addCategory("Yellow (Java)", Icon.right){ table ->

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
        }
    }
}