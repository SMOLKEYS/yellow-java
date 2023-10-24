package yellow.internal.util

import arc.graphics.Color
import arc.scene.event.Touchable
import arc.scene.ui.Image
import arc.scene.ui.layout.*
import arc.struct.Seq
import kotmindy.mindustry.MUnit
import mindustry.game.Team
import mindustry.gen.Groups
import mindustry.type.UnitType

fun MUnit.healthFract() = this.health / this.type.health

infix fun Int.ins(other: Int) = this % other == 0

infix fun Float.ins(other: Float) = this % other == 0f

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

fun Boolean.yesNo() = if(this) "@yes" else "@no"

fun seperator(table: Table, width: Float): Cell<Image> = seperator(table, width, 10f)

fun seperator(table: Table, width: Float, height: Float): Cell<Image> = seperator(table, width, height, Color.darkGray)

fun seperator(table: Table, width: Float, height: Float, color: Color): Cell<Image> = table.image().width(width).height(height).color(color)

