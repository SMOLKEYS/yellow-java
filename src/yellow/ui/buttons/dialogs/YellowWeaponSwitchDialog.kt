package yellow.ui.buttons.dialogs

import com.github.mnemotechnician.mkui.extensions.dsl.textButton
import mindustry.entities.units.WeaponMount
import mindustry.ui.dialogs.BaseDialog
import yellow.Yellow
import yellow.entities.units.DisableableWeaponMount
import yellow.type.NameableWeapon

open class YellowWeaponSwitchDialog: BaseDialog("Weapon Switch") {
    init {
        addCloseButton()
    }

    fun show(weapon: Array<WeaponMount>) {
        cont.clear()
        weapon.forEach {
            if(it !is DisableableWeaponMount) return
            cont.check((it.weapon as NameableWeapon).displayName, it.enabled) { a: Boolean ->
                it.enabled = a
                if(it.enabled) it.enabled() else it.disabled()
            }
            cont.textButton("?"){
                Yellow.weaponInfo.show(it.weapon as NameableWeapon)
            }.row()
        }

        super.show()
    }
}
