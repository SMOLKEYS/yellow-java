package yellow.world.blocks.units

import arc.graphics.*
import arc.graphics.g2d.*
import arc.math.*
import arc.scene.event.*
import arc.scene.ui.layout.*
import arc.util.*
import arc.util.io.*
import mindustry.content.*
import mindustry.entities.*
import mindustry.gen.*
import mindustry.graphics.*
import mindustry.type.*
import mindustry.world.*
import mindustry.world.meta.*
import yellow.internal.util.*

open class SummoningShrine(
    /** What unit to summon.  */
    var unit: UnitType
) : Block(unit.name + "-shrine") {
    /** Time required for the unit to be summoned.  */
    var summonTime = 60f

    /** Whether the block sprite should be drawn on the block. */
    var drawBlock = false

    /** What effect to call on summon request.  */
    var requestEffect: Effect = Fx.none

    /** What effect to call once the unit gets successfully summoned.  */
    var summonEffect: Effect = Fx.none

    init {
        configurable = true //set this to false, and you basically just have a Block lmao
        solid = true
        update = true
        rotate = false
        buildVisibility = BuildVisibility.sandboxOnly //fn, ig
    }

    override fun setStats() {
        super.setStats()
        stats.add(Stat.output){
            it.row()
            YellowUtils.unitBar(it, unit, "Summon Time: [accent]${Mathf.round(summonTime / 60f)} Seconds[]")
        }
    }

    inner class SummoningShrineBuild : Building() {

        private var currentlySummoning = false
        private var placed = false
        private var a = 0f
        private var size = 0f

        override fun buildConfiguration(table: Table) {
            table.table { t: Table ->
                t.add("Summoning Shrine (${unit.localizedName})").row()
                t.button("Summon Unit") {
                    t.touchableOf(1, Touchable.disabled)
                    requestEffect.at(this)
                    currentlySummoning = true
                    Time.run(summonTime) {
                        if (!unit.flying && !unit.canBoost) {
                            unit.spawn(team, x + 8f * 5f, y)
                            summonEffect.at(x + 8f * 5f, y)
                        } else {
                            unit.spawn(team, this)
                            summonEffect.at(this)
                        }
                        currentlySummoning = false
                        t.touchableOf(1, Touchable.enabled)
                    }
                }.get().label.setWrap(false)
                if (currentlySummoning) {
                    t.touchableOf(1, Touchable.enabled)
                }
            }
        }

        override fun placed() {
            super.placed()
            placed = true
        }

        override fun draw() {
            if (drawBlock) {
                Drawf.shadow(region, x, y, 0f)
                Draw.rect(region, x, y, 0f)
            }
            val lerpA = if (currentlySummoning) 1f else 0f
            val sus = Mathf.absin(10f, 10f)
            
            a = Mathf.lerp(a, lerpA, 0.04f)
            
            val lerpSize = if (placed) 20f else 0f
            size = Mathf.lerp(size, lerpSize, 0.043f)
            Draw.z(Layer.effect)
            Draw.color(Tmp.c1.set(Color.yellow).lerp(Color.cyan, Mathf.absin(10f, 1f)))
            Fill.circle(x, y, size - 15f + Mathf.absin(10f, 2f))
            Lines.circle(x, y, size)
            Lines.square(x, y, size - 1f, Time.time)
            Lines.square(x, y, size - 1f, -Time.time)
            Draw.alpha(a)
            Lines.circle(x, y, 25f + sus)
            Lines.square(x, y, 25f + sus, Time.time)
            Lines.square(x, y, 25f + sus, -Time.time)
        }

        override fun write(write: Writes) {
            super.write(write)
            write.bool(placed)
        }

        override fun read(read: Reads, revision: Byte) {
            super.read(read, revision)
            placed = read.bool()
        }
    }
}
