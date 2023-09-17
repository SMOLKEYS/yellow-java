package yellow.ui

import arc.Core
import arc.files.Fi
import com.github.mnemotechnician.mkui.extensions.dsl.textButton
import mindustry.Vars.ui
import mindustry.gen.Icon
import mindustry.ui.dialogs.SettingsMenuDialog
import yellow.*
import yellow.content.YellowNotifications

object YellowSettings{

    val tmpDir: Fi = Core.settings.dataDirectory.child("yellow.jar")

    @JvmStatic
    fun load(){
        ui.settings.addCategory("Yellow (Java)", Icon.right){ table ->
            
            table.checkPref("internalloggering", false){
                YellowPermVars.internalLoggering = it
            }
            
            table.checkPref("weaponsanity", true){
                YellowPermVars.weaponSanityCheck = it
            }
            
            table.textPref("sourceberepo", YellowPermVars.sourceBERepo){
                if(it.isBlank()){
                    YellowPermVars.sourceBERepo = "https://github.com/SMOLKEYS/yellow-java-builds/releases/latest/download/yellow-java.jar"
                }else{
                    YellowPermVars.sourceBERepo = it
                }
            }
            
            table.textPref("sourcerepo", YellowPermVars.sourceReleaseRepo){
                if(it.isBlank()){
                    YellowPermVars.sourceReleaseRepo = "https://github.com/SMOLKEYS/yellow-java/releases/latest/download/yellow-java.jar"
                }else{
                    YellowPermVars.sourceReleaseRepo = it
                }
            }

            table.pref(ButtonSetting("notifs"){
                YellowVars.notifs.show(Notification.instances)
            })

            table.pref(ButtonSetting("callnotif"){
                YellowNotifications.hi.add()
            })
        }
    }

    class ButtonSetting(name: String, var clicked: () -> Unit): SettingsMenuDialog.SettingsTable.Setting(name){

        override fun add(table: SettingsMenuDialog.SettingsTable) {
            table.textButton(title, wrap = false){
                clicked()
            }.growX().row()
        }
    }
}
