@file:Suppress("unused")

package yellow.world.blocks.units

import arc.math.Mathf
import arc.scene.event.Touchable
import arc.scene.ui.layout.Table
import arc.util.Time
import arc.util.io.*
import com.github.mnemotechnician.mkui.extensions.dsl.*
import mindustry.content.Fx
import mindustry.entities.Effect
import mindustry.gen.Building
import mindustry.type.*
import mindustry.world.Block
import mindustry.world.meta.*
import yellow.util.*

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

    var oneOnly: Boolean = false

    init {
        configurable = true
        solid = true
        update = true
        rotate = false
        buildVisibility = BuildVisibility.sandboxOnly //fn, ig
        category = Category.effect
    }

    override fun setStats() {
        super.setStats()
        stats.add(Stat.output){
            it.row()
            UnitStuffs.unitBar(it, unit, "Summon Time: [accent]${Mathf.round(summonTime / 60f)} Seconds[]")
        }
    }

    inner class SummoningShrineBuild : Building() {

        private var currentlySummoning = false
        private var placed = false

        override fun buildConfiguration(table: Table) {
            table.addTable {
                addLabel("${this@SummoningShrine.localizedName} (${unit.localizedName})", wrap = false).growX().row()
                textButton("@summon", wrap = false){
                    currentlySummoning = true
                    request()
                    Time.run(summonTime){
                        summonUnit()
                        currentlySummoning = false
                    }
                }.update{
                    it.touchable = if(currentlySummoning || oneOnly && unit.exists(team)) Touchable.disabled else Touchable.enabled
                }
            }.grow()
        }

        private fun request(){
            requestEffect.at(this)
        }

        private fun summonUnit(){
            unit.spawn(this, team)
        }

        override fun placed() {
            super.placed()
            placed = true
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
