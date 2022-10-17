package yellow.internal.util

import arc.files.Fi
import arc.struct.Seq

object YellowUtilsKt{
    fun traverse(dir: Fi, dump: Seq<String>){
        dir.seq().each{ su: Fi ->
            dump.add(su.pathWithoutExtension().toString().replace("/", ".").removeSuffix("."))
            if(!su.seq().isEmpty) traverse(su, dump)
        }
    }
}