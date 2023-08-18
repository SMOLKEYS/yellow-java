package yellow.ui.buttons.dialogs

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
        /*
        cont.add("""
            Weapon Switch System
            Each checkbox here is linked to the unit weapon associated with this dialog.
            You can toggle any weapon here. Have fun!
            [gray]TODO: Non-corruptive save I/O, weapon state inheritance, and perhaps weapon descriptions?
            Will probably need a seperate dialog to view in, keep that in mind.[]
        """.trimIndent()).row()
        */
        weapon.forEach {
            if(it !is DisableableWeaponMount) return
            cont.check((it.weapon as NameableWeapon).displayName, it.enabled) { a: Boolean ->
                it.enabled = a
                if(it.enabled) it.enabled() else it.disabled()
            }.row()
        }

        super.show()
    }
}
