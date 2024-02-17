package yellow.entities.units

import arc.Core
import arc.func.*
import arc.graphics.Color
import arc.math.Mathf
import arc.scene.ui.Image
import arc.scene.ui.layout.Table
import arc.struct.Seq
import arc.util.Scaling
import kotmindy.mindustry.MUnit
import mindustry.Vars
import mindustry.ai.UnitCommand
import mindustry.ai.types.LogicAI
import mindustry.content.Blocks
import mindustry.gen.*
import mindustry.gen.Unit
import mindustry.graphics.Pal
import mindustry.type.UnitType
import mindustry.ui.Bar
import mindustry.world.meta.*
import yellow.*
import yellow.entities.units.entity.YellowUnitEntity
import yellow.type.*
import yellow.util.seperator
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

    override fun display(unit: Unit, table: Table) {
        table.table {t: Table ->
            t.left()
            t.add(Image(fullIcon)).size((8 * 4).toFloat()).scaling(Scaling.fit)
            t.labelWrap(localizedName).left().width(190f).padLeft(5f)
        }.growX().left()
        table.row()

        table.table {bars: Table ->
            bars.defaults().growX().height(20f).pad(4f)
            val ent = unit as YellowUnitEntity
            bars.add(Bar("stat.health", Color.red) {ent.healthf()})
            bars.row()
            bars.add(Bar("stat.lives", Pal.accent) {ent.livesf()})
            bars.row()
        }.growX()

        if(unit.controller() is LogicAI) {
            table.row()
            table.add(Blocks.microProcessor.emoji() + " " + Core.bundle["units.processorcontrol"]).growX().wrap().left()
            table.row()
            table.label {Iconc.settings.toString() + " " + unit.flag.toLong()}.color(Color.lightGray).growX().wrap().left()
        }
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
                    me.button(suse.nameLocalized()){
                        YellowVars.weaponInfo.show(it)
                    }.growX().wrapLabel(false)
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
