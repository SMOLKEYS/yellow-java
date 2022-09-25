package yellow.ui.buttons.dialogs;

import arc.scene.ui.*;
import mindustry.*;
import mindustry.ui.dialogs.*;
import mindustry.entities.units.*;
import yellow.type.*;
import yellow.content.*;
import yellow.weapons.*;
import yellow.entities.units.*;
import yellow.entities.units.DisableableWeaponMount;

open class WeaponSwitchDialog: BaseDialog("Weapon Switch") {
    init {
        addCloseButton()
    }

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
