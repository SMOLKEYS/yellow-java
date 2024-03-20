package yellow.entities.units

import arc.Core
import arc.func.*
import arc.graphics.Color
import arc.math.Mathf
import arc.scene.ui.Image
import arc.scene.ui.layout.Table
import arc.struct.Seq
import arc.util.Scaling
import com.github.mnemotechnician.mkui.extensions.dsl.addLabel
import kotmindy.mindustry.MUnit
import mindustry.Vars
import mindustry.ai.UnitCommand
import mindustry.ai.types.LogicAI
import mindustry.content.Blocks
import mindustry.entities.bullet.ContinuousFlameBulletType
import mindustry.gen.*
import mindustry.gen.Unit
import mindustry.graphics.Pal
import mindustry.type.UnitType
import mindustry.ui.*
import mindustry.world.meta.*
import yellow.YellowPermVars
import yellow.entities.units.entity.YellowUnitEntity
import yellow.goodies.vn.*
import yellow.type.*
import yellow.util.*
import yellow.world.meta.YellowStats

open class YellowUnitType(name: String): UnitType(name), CharacterUnit {

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
            weapons.each{w ->
                val suse = w as DisableableWeapon
                if(!suse.mirroredVersion){
                    me.add(suse.nameLocalized()).tooltip {n ->

                        n.background = Styles.grayPanel

                        val info = Table()
                        info.margin(10f)

                        info.table{
                            it.image(Core.atlas.drawable("status-disarmed")).size(50f)

                            val dat = if(Core.settings.getBool("console")) "\n[gray]${suse.name}[]" else ""

                            it.add("[accent]${suse.nameLocalized()}[]$dat").padLeft(5f)
                        }.row()

                        info.add("@description").color(Pal.accent).fillX().padTop(10f).row()

                        info.add(suse.description).color(Color.lightGray).fillX().padLeft(10f)
                        info.row()

                        info.add("@general").color(Pal.accent).fillX().padTop(3f).row()

                        if(suse.bullet !is ContinuousFlameBulletType) info.addLabel(Core.bundle.format("weapon.reload", suse.reload / 60, "seconds"), wrap = false).left().padLeft(10f).row()
                        info.addLabel(Core.bundle.format("weapon.damage", suse.bullet.damage, suse.bullet.estimateDPS()) + " ${Iconc.info}", wrap = false).pulseTooltip(Core.bundle["weapon.damage.warning"], Color.white, Color.orange, 10f).left().padLeft(10f).row()
                        info.addLabel(Core.bundle.format("weapon.range", suse.range() / 8) + " ${Iconc.info}", wrap = false).pulseTooltip(Core.bundle["weapon.range.warning"], Color.white, Color.orange, 10f).left().padLeft(10f).row()
                        info.addLabel(Core.bundle.format("weapon.offset", suse.x, suse.y), wrap = false).left().padLeft(10f).row()
                        info.addLabel(Core.bundle.format("weapon.rotate", suse.rotate.yesNo()), wrap = false).left().padLeft(10f).row()
                        info.addLabel(Core.bundle.format("weapon.shootcone", suse.shootCone), wrap = false).left().padLeft(10f).row()
                        info.addLabel(if(suse.rotate) Core.bundle.format("weapon.rotatespeed", suse.rotateSpeed) else Core.bundle.format("weapon.baserotation", suse.baseRotation), wrap = false).left().padLeft(10f).row()

                        n.add(info).grow().left()
                    }.left().padLeft(10f).row()
                    seperator(me, 450f, 4f, Color.darkGray).padTop(5f).row()
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

    override fun getCharacter(): InteractiveCharacter? = null
}
