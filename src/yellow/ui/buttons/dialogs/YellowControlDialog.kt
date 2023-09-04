package yellow.ui.buttons.dialogs

import arc.flabel.FLabel
import arc.graphics.Color
import arc.scene.ui.ScrollPane
import arc.scene.ui.layout.Table
import com.github.mnemotechnician.mkui.extensions.dsl.*
import mindustry.Vars
import mindustry.entities.units.WeaponMount
import mindustry.ui.Bar
import mindustry.ui.Styles
import mindustry.ui.dialogs.BaseDialog
import yellow.*
import yellow.entities.units.DisableableWeaponMount
import yellow.entities.units.entity.YellowUnitEntity
import yellow.internal.util.*
import yellow.type.*

open class YellowControlDialog: BaseDialog("@yellowcontrol") {
    init {
        addCloseButton()
    }

    fun show(weapon: Array<WeaponMount>) = show(weapon, null)

    fun show(weapon: Array<WeaponMount>, unit: YellowUnitEntity?) {
        cont.clear()

        val weapons = Table()
        val misc = Table()
        val info = Table()

        if(!Vars.mobile && unit != null){
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
                    add(FLabel("i am clearly not making use of any of this space\nNOR properly using ui")).left().row()
                }
            }.grow()
        }.grow()

        cont.add(weapons).grow().let { if(Vars.mobile) row() }

        if(!Vars.mobile){
            val subTable = Table()
            subTable.add(misc).grow().row()
            subTable.add(info).grow().row()
            cont.add(subTable).grow()
        }else{
            cont.add(info).grow()
        }

        super.show()
    }
}