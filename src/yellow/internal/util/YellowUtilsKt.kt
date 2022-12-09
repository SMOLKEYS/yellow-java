package yellow.internal.util

import arc.*
import arc.files.*
import arc.graphics.*
import arc.scene.event.*
import arc.scene.ui.*
import arc.scene.ui.layout.*
import arc.struct.*
import arc.util.*
import arc.util.serialization.*
import mindustry.*
import yellow.type.*


typealias MUnit = mindustry.gen.Unit

fun MUnit.healthFract() = this.health / this.type.health

infix fun Int.ins(other: Int) = this % other == 0

fun <T> seqOf(vararg items: T): Seq<T>{
    if(items.isEmpty()) return Seq.with();
    return Seq.with(*items);
}
fun Table.touchableOf(index: Int, touchable: Touchable){
    this.children[index].touchable = touchable
}

fun typiis(item: FoodItem): String{
    return buildString{
        if(item.healUsingPercentage) append("+${(item.healingPercent * 100f).toInt()}% HP") else append("+${item.healing} HP") 
        if(item.healAllAllies) append(" (Team)") else append(" (Single)")
    }
}

object YellowUtilsKt{
    private val jsr = JsonReader()
    
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
    
    fun getBleedingEdgeVersion(): Int{
        var kr = 0
        
        Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java-builds/releases", {
            val res = it.getResultAsString()
            
            try{
                val version = jsr.parse(res)["name"].name
                kr = if(version != null) version.toInt() else 0 //anything can happen.
            }catch(e: Exception){
                Vars.ui.showException("Bleeding Edge Version GET Error", e)
            }
        }, {
            Core.app.post{
                it.printStackTrace()
                Vars.ui.showException("Bleeding Edge Version GET Error", it)
            }
        })
        
        return kr
    }
    
    fun seperator(table: Table, width: Float): Cell<Image> = seperator(table, width, 10f)

    fun seperator(table: Table, width: Float, height: Float): Cell<Image> = seperator(table, width, height, Color.darkGray)
    
    fun seperator(table: Table, width: Float, height: Float, color: Color): Cell<Image> = table.image().width(width).height(height).color(color)
}
