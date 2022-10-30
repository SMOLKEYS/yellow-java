package yellow.internal.util

import arc.Core
import arc.scene.ui.Image
import arc.scene.ui.layout.*
import arc.files.Fi
import arc.graphics.Color
import arc.struct.Seq
import arc.util.Log
import arc.util.serialization.JsonValue


infix fun Int.ins(other: Int) = this % other == 0
object YellowUtilsKt{
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
