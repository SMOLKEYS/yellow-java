package yellow.entities.units

import arc.func.*
import arc.math.Mathf
import arc.scene.ui.layout.Table
import arc.struct.Seq
import kotmindy.mindustry.MUnit
import mindustry.Vars
import mindustry.ai.UnitCommand
import mindustry.game.Team
import mindustry.gen.Unit
import mindustry.type.UnitType
import mindustry.world.meta.*
import yellow.*
import yellow.entities.units.entity.YellowUnitEntity
import yellow.internal.util.YellowUtilsKt.seperator
import yellow.type.*
import yellow.world.meta.YellowStats

open class YellowUnitType(name: String): UnitType(name) {

    var maxLives = 5
    var afterDeath: Array<Cons<YellowUnitEntity>?> = arrayOfNulls(maxLives)

    @JvmField
    var spells = Seq<Spell>()

    init{
        constructor = Prov<MUnit> {YellowUnitEntity()}
        defaultCommand = UnitCommand.assistCommand

        if(afterDeath.size != maxLives) throw ArrayIndexOutOfBoundsException("onDeath.size not equal to maxLives")
    }
    
    override fun setStats(){
        super.setStats()
        stats.useCategories = true
        stats.remove(Stat.health)
        stats.add(Stat.health, "${Mathf.round(health * maxLives)} (${health.toInt()} x $maxLives)")
        stats.remove(Stat.itemCapacity)
        stats.add(YellowStats.itemCapacityAlt, "$itemCapacity")
        stats.remove(Stat.weapons)
        stats.add(YellowStats.weaponsAlt){ me: Table ->
            me.add().row()
            seperator(me, 290f, 4f)
            me.row()
            weapons.each{
                val suse = it as DisableableWeapon
                if(!suse.mirroredVersion){
                    me.add(suse.nameLocalized())
                    me.button("?"){
                        YellowVars.weaponInfo.show(it)
                    }.size(35f)
                    me.row()
                    seperator(me, 290f, 4f)
                    me.row()
                }
            }
        }
        stats.remove(Stat.range)
        stats.add(YellowStats.rangeAlt, "${(maxRange / Vars.tilesize).toInt()}", StatUnit.blocks)
        
        
        stats.add(YellowStats.maxLives, "$maxLives")
        stats.add(YellowStats.extras, """
        1000-8000 [cyan]shield health[] on first death
        Random chance of teleporting frantically on last life
        Random chance of teleporting frantically AND dropping plasma bombs on last life
        """.trimIndent())
        
        stats.add(YellowStats.name, "Nihara")
        stats.add(YellowStats.gender, "Female")
        stats.add(YellowStats.age, "23", YellowStats.yearsOld)
        stats.add(YellowStats.affinity, "${YellowPermVars.affinity} / 100")
    }
}
