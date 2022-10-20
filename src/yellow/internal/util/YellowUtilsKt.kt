package yellow.internal.util

import arc.Core
import arc.util.Log
import arc.util.serialization.JsonValue
import arc.files.Fi
import arc.struct.Seq

object YellowUtilsKt{
    fun traverse(dir: Fi, dump: Seq<String>){
        if(!dir.exists()) return
        dir.seq().each{ su: Fi ->
            if(su.isDirectory()) dump.add(su.pathWithoutExtension().toString().replace("/", ".").removeSuffix("."))
            if(!su.seq().isEmpty) traverse(su, dump)
        }
    }
    
    fun traverse(dir: Fi){
        if(!dir.exists()) return
        dir.seq().each{ su: Fi -> 
            if(su.isDirectory()) Log.info(su.pathWithoutExtension().toString().replace("/", ".").removeSuffix("."))
            if(!su.seq().isEmpty) traverse(su)
        }
    }
    
    fun JsonValue.getValues(vararg values: String): String{
        val aus = this
        val ignoredBundleJsonValues = listOf("name", "display_title", "id", "run_number")
        
        return buildString{
            values.forEach{ ba: String ->
                val sua = "${aus[ba]}".split(":")
                ignoredBundleJsonValues.forEach{ ab -> String
                    if(ba == ab){
                        append(Core.bundle["status.${sua[0].replace("_", "-")}"] + ": ${sua[1].trim()}\n")
                    }else{
                        append(Core.bundle["status.${sua[0].replace("_", "-")}"] + ": " + Core.bundle["status.${sua[1].replace("_", "-")}"]} + "\n")
                    }
                }
            }
        }
    }
}
