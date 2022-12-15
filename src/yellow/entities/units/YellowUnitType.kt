package yellow.entities.units

import arc.func.*
import arc.graphics.*
import arc.graphics.g2d.*
import arc.math.*
import arc.scene.ui.layout.*
import arc.util.*
import mindustry.*
import mindustry.ai.*
import mindustry.graphics.*
import mindustry.type.*
import mindustry.world.meta.*
import yellow.entities.units.entity.*
import yellow.internal.util.YellowUtilsKt.seperator
import yellow.type.*
import yellow.world.meta.*
import kotmindy.mindustry.*

open class YellowUnitType(name: String): UnitType(name) {

    var maxLives = 5

    init{
        constructor = Prov<MUnit> {YellowUnitEntity()}
        defaultCommand = UnitCommand.assistCommand
    }

    override fun draw(unit: MUnit) {
        super.draw(unit)

        var s = Mathf.absin(Time.time, 16f, 1f)
        var r1 = s * 25f
        var r2 = s * 20f

        Draw.z(Layer.effect)
        Draw.color(Color.yellow)

        Lines.circle(unit.x, unit.y, 20f + r1)
        Lines.square(unit.x, unit.y, 20f + r1, Time.time)
        Lines.square(unit.x, unit.y, 20f + r1, -Time.time)

        Tmp.v1.trns(Time.time, r2, r2)

        Fill.circle(unit.x + Tmp.v1.x, unit.y + Tmp.v1.y, 2f + s * 8f)
        Tmp.v1.trns(Time.time, -r2, -r2)
        Fill.circle(unit.x + Tmp.v1.x, unit.y + Tmp.v1.y, 2f + s * 8f)
        Tmp.c1.set(Color.white)
        Tmp.c1.a = 0f
        Fill.light(unit.x, unit.y, 5, 50f - r1, Color.yellow, Tmp.c1)
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
                    me.add(suse.displayName)
                    me.button("?"){}.size(35f)
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
        Fourth-wall breaker (Pilot/Human Form)
        """.trimIndent())
        
        stats.add(YellowStats.name, "Nihara")
        stats.add(YellowStats.gender, "Female")
        stats.add(YellowStats.age, "23", YellowStats.yearsOld)
        stats.add(YellowStats.personality, "Kind/Friendly")
        stats.add(YellowStats.headpatRating, "High")
        stats.add(YellowStats.generalAura, "Menacing (First Encounter)")
        stats.add(YellowStats.loveInterest, ".....")
        stats.add(YellowStats.likes, "Comfort, Yellow-colored things, etc...")
        stats.add(YellowStats.dislikes, "Anything explosive, especially Thorium Reactors\n[gray](with the exception of her own weapons in Unit form)[]")
    }
}
