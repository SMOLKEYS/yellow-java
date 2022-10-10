package yellow.ui.buttons.dialogs;

import mindustry.entities.units.WeaponMount
import mindustry.ui.dialogs.BaseDialog
import yellow.entities.units.DisableableWeaponMount
import yellow.type.NameableWeapon

open class YellowWeaponSwitchDialog: BaseDialog("Weapon Switch") {
    init {
        addCloseButton()
    }
    
    //TODO add more variety control over weapons because why not
    fun show(weapon: Array<WeaponMount>) {
        cont.clear()

        weapon.forEach {
            if(it !is DisableableWeaponMount) return
            cont.check("${(it.weapon as NameableWeapon).displayName}", it.enabled) {a: Boolean ->
                it.enabled = a
            }.row()
        }

        super.show()
    }
}
