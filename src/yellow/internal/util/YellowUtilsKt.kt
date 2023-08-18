package yellow.internal.util

import arc.Core
import arc.files.Fi
import arc.graphics.Color
import arc.scene.event.Touchable
import arc.scene.ui.Image
import arc.scene.ui.layout.*
import arc.struct.Seq
import arc.util.Log
import arc.util.serialization.*
import kotmindy.mindustry.MUnit

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

fun String.tint(color: Color) = "[#${color.toString()}]$this[]"

fun Boolean.yesNo() = if(this) "Yes" else "No"

object YellowUtilsKt{
    private val jsr = JsonReader()
   
   fun rangeOf(one: Int, two: Int) = one..two
   
    fun traverse(dir: Fi, dump: Seq<String>){
        if(!dir.exists()) return
        dir.seq().each{ su: Fi ->
            if(su.isDirectory) dump.add(su.pathWithoutExtension().toString().replace("/", ".").removeSuffix("."))
            if(!su.seq().isEmpty) traverse(su, dump)
        }
    }
    
    fun traverse(dir: Fi){
        if(!dir.exists()) return
        dir.seq().each{ su: Fi -> 
            if(su.isDirectory) Log.info(su.pathWithoutExtension().toString().replace("/", ".").removeSuffix("."))
            if(!su.seq().isEmpty) traverse(su)
        }
    }
    
    fun JsonValue.getValues(vararg values: String): String{
        val aus = this
        
        return buildString{
            values.forEach{
                val sua = "${aus[it]}".split(":")
                append(Core.bundle["status.${sua[0].replace("_", "-")}"] + ": ${sua[1].trim()}\n")
            }
        }
    }
    
    
    fun seperator(table: Table, width: Float): Cell<Image> = seperator(table, width, 10f)

    fun seperator(table: Table, width: Float, height: Float): Cell<Image> = seperator(table, width, height, Color.darkGray)
    
    fun seperator(table: Table, width: Float, height: Float, color: Color): Cell<Image> = table.image().width(width).height(height).color(color)
}
