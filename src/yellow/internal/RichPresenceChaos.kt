package yellow.internal

import arc.struct.Seq
import mindustry.Vars
import yellow.util.readLines

object RichPresenceChaos{
    @JvmStatic
    val rpc = Seq<String>()

    @JvmStatic
    fun load(){
        val rpcd = Vars.tree["discord/rpc.txt"].readLines()
        rpc.addAll(rpcd)
    }
}