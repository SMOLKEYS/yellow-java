package yellow.internal.util

import arc.Core
import arc.graphics.Color
import arc.math.Interp
import arc.scene.event.Touchable
import arc.scene.ui.Image
import arc.scene.ui.layout.*
import arc.struct.Seq
import mindustry.game.Team
import mindustry.gen.Groups
import mindustry.type.UnitType

infix fun Int.ins(other: Int) = this % other == 0

infix fun Float.ins(other: Float) = this % other == 0f

fun Float.interpolate(interp: Interp) = interp.apply(this)

fun Float.interpolate(interp: Interp, multiplier: Float) = this@interpolate.interpolate(interp) * multiplier

fun Float.perc() = 1f / this

fun <T> seqOf(vararg items: T): Seq<T>{
    if(items.isEmpty()) return Seq.with();
    return Seq.with(*items);
}

fun Table.touchableOf(index: Int, touchable: Touchable){
    this.children[index].touchable = touchable
}

fun UnitType.exists(team: Team): Boolean{
    return Groups.unit.contains{ it.type == this && it.team == team }
}

fun String.tint(color: Color) = "[#$color]$this[]"

fun Boolean.yesNo() = Core.bundle[if(this) "yes" else "no"]

fun seperator(table: Table, width: Float): Cell<Image> = seperator(table, width, 10f)

fun seperator(table: Table, width: Float, height: Float): Cell<Image> = seperator(table, width, height, Color.darkGray)

fun seperator(table: Table, width: Float, height: Float, color: Color): Cell<Image> = table.image().width(width).height(height).color(color)

