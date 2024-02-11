package yellow.ui

import arc.Core
import arc.files.Fi
import arc.discord.*
import com.github.mnemotechnician.mkui.extensions.dsl.textButton
import mindustry.Vars
import mindustry.Vars.ui
import mindustry.gen.Icon
import mindustry.ui.dialogs.SettingsMenuDialog
import yellow.*
import yellow.content.*
import yellow.game.Notification

object YellowSettings{

    val tmpDir: Fi = Core.settings.dataDirectory.child("yellow-java.jar")

    @JvmStatic
    fun load(){
        ui.settings.addCategory("Yellow", Icon.right){ table ->
            
            table.checkPref("internalloggering", false){
                YellowPermVars.internalLoggering = it
            }
            
            table.checkPref("weaponsanity", true){
                YellowPermVars.weaponSanityCheck = it
            }

            if(!Vars.mobile) table.checkPref("enablerpc", false){
                YellowPermVars.enableRpc = it
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
            
            table.areaTextPref("activeeffect", YellowDrawEffects.activeEffect.name){
                if(it.isBlank()){
                    YellowDrawEffects.activeEffect = YellowDrawEffects.effects[0]
                }else {
                    YellowDrawEffects.activeEffect = YellowDrawEffects.effects.find {e -> e.name == it}
                            ?: YellowDrawEffects.effects[0]
                }
            }

            table.pref(ButtonSetting("notifs"){
                YellowVars.notifs.show(Notification.instances)
            })

            table.pref(ButtonSetting("callnotif"){
                YellowNotifications.hi?.add()
            })

            table.pref(ButtonSetting("achievements"){
                YellowVars.achievements.show()
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
