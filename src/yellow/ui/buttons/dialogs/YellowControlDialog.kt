package yellow.ui.buttons.dialogs

import arc.graphics.Color
import arc.scene.event.Touchable
import arc.scene.ui.TextButton
import arc.scene.ui.layout.Table
import com.github.mnemotechnician.mkui.extensions.dsl.*
import mindustry.entities.units.WeaponMount
import mindustry.ui.dialogs.BaseDialog
import yellow.YellowVars
import yellow.entities.units.DisableableWeaponMount
import yellow.entities.units.entity.*
import yellow.type.*

open class YellowControlDialog: BaseDialog("@yellowcontrol") {
    init {
        addCloseButton()
    }

    fun show(weapon: Array<WeaponMount>, spell: Array<SpellBind>, unit: YellowUnitEntity?) {
        cont.clear()

        val weapons = Table()
        val spells = Table()
        val misc = Table()
        val info = Table()

        val buttone = TextButton("@exit")


        if(unit != null){
            misc.addTable {
                addLabel("@misc").row()
                image().color(Color.darkGray).height(6f).growX()
            }.growX().top().row()
            misc.addTable {
                scrollPane {
                    check("@sentryidle", unit.forceIdle) {a: Boolean ->
                        unit.forceIdle = a
                    }.left().update{ it.isChecked = unit.forceIdle }.row()
                    check("@enableautoidle", unit.enableAutoIdle) {a: Boolean ->
                        unit.enableAutoIdle = a
                    }.left().update{ it.isChecked = unit.enableAutoIdle }.row()
                    textButton("@removefrommap") {
                        unit.despawn()
                        hide()
                    }.growX()
                    textButton("@killyo") {
                        unit.kill()
                        if(unit.lives == 0) hide()
                    }.growX().row()
                }.grow()
            }.grow()
        }

        weapons.addTable {
            addLabel("@weapons").row()
            image().color(Color.darkGray).height(6f).growX()
        }.growX().top().row()
        weapons.addTable {
            scrollPane {
                weapon.forEach {mount ->
                    if(mount !is DisableableWeaponMount) return
                    if(!(mount.weapon as DisableableWeapon).mirroredVersion) {
                        check((mount.weapon as NameableWeapon).displayName, mount.enabled) {a: Boolean ->
                            mount.enabled = a
                            if(mount.enabled) mount.enabled() else mount.disabled()
                        }.update{ it.isChecked = mount.enabled }.left()
                        textButton("?") {
                            YellowVars.weaponInfo.show(mount.weapon as NameableWeapon)
                        }.row()
                    } else {
                        check((mount.weapon as NameableWeapon).displayName, mount.enabled) {a: Boolean ->
                            mount.enabled = a
                            if(mount.enabled) mount.enabled() else mount.disabled()
                        }.update{ it.isChecked = mount.enabled }.left().row()
                    }
                }

                textButton("@enableallweapons"){
                    weapon.forEach {
                        if(it is DisableableWeaponMount) {
                            it.enabled = true
                            it.enabled()
                        }
                    }
                }.growX()
                textButton("@disableallweapons"){
                    weapon.forEach {
                        if(it is DisableableWeaponMount) {
                            it.enabled = false
                            it.disabled()
                        }
                    }
                }.growX().row()
            }.grow()
        }.grow()

        spells.addTable {
            addLabel("@spells").row()
            image().color(Color.darkGray).height(6f).growX()
        }.growX().top().row()
        spells.addTable {
            scrollPane {
                spell.forEach {bind ->
                    textButton(bind.spell.displayName){
                        bind.cast(unit)
                    }.update { touchable = if(bind.ready()) Touchable.enabled else Touchable.disabled }.growX().row()
                }
            }.grow()
        }.grow()

        info.addTable {
            addLabel("@unitinfo").row()
            image().color(Color.darkGray).height(6f).growX()
        }.growX().top().row()
        info.addTable {
            scrollPane {
                if(unit != null) {
                    addLabel({ "Health: ${unit.health}/${unit.maxHealth.toInt()}" }).left().row()
                    addLabel({ "Lives: ${unit.lives}/${unit.type().maxLives}" }).left().row()
                    addLabel({ "Shield: ${unit.shield}" }).left().row()
                    addLabel({ "Idle Time: ${unit.idleTime}" }).left().row()
                }
            }.grow()
        }.grow()


        //shitcode one

        fun back(){
            cont.textButton("@weapons"){
                cont.clear()
                cont.add(weapons).grow().row()
                cont.add(buttone).growX()
            }.growX().row()
            cont.textButton("@spells"){
                cont.clear()
                cont.add(spells).grow().row()
                cont.add(buttone).growX()
            }.growX().row()
            cont.textButton("@unitinfo"){
                cont.clear()
                cont.add(info).grow().row()
                cont.add(buttone).growX()
            }.growX().row()
            cont.textButton("@misc"){
                cont.clear()
                cont.add(misc).grow().row()
                cont.add(buttone).growX()
            }.growX().row()
        }

        buttone.clicked {
            cont.clear()
            back()
        }

        back()

        super.show()
    }
}