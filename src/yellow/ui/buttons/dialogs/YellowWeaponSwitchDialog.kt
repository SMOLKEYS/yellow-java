package yellow.ui.buttons.dialogs

import com.github.mnemotechnician.mkui.extensions.dsl.textButton
import mindustry.Vars
import mindustry.entities.units.WeaponMount
import mindustry.ui.dialogs.BaseDialog
import yellow.Yellow
import yellow.entities.units.DisableableWeaponMount
import yellow.entities.units.entity.YellowUnitEntity
import yellow.type.*

open class YellowWeaponSwitchDialog: BaseDialog("Weapon Switch") {
    init {
        addCloseButton()
    }

    fun show(weapon: Array<WeaponMount>) = show(weapon, null)

    fun show(weapon: Array<WeaponMount>, unit: YellowUnitEntity?) {
        cont.clear()
        weapon.forEach {
            if(it !is DisableableWeaponMount) return
            if(!(it.weapon as DisableableWeapon).mirroredVersion){
                cont.check((it.weapon as NameableWeapon).displayName, it.enabled) {a: Boolean ->
                    it.enabled = a
                    if(it.enabled) it.enabled() else it.disabled()
                }
                cont.textButton("?") {
                    Yellow.weaponInfo.show(it.weapon as NameableWeapon)
                }.row()
            }else{
                cont.check((it.weapon as NameableWeapon).displayName, it.enabled) {a: Boolean ->
                    it.enabled = a
                    if(it.enabled) it.enabled() else it.disabled()
                }.row()
            }
        }
        if(!Vars.mobile && unit != null) cont.check("@sentryidle", unit.forceIdle){a: Boolean ->
            unit.forceIdle = a
        }

        super.show()
    }
}
