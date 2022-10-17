package yellow.internal.util

import arc.util.Log
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
}
