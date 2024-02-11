package yellow.internal

import arc.struct.Seq
import mindustry.Vars
import mindustry.mod.Mods
import yellow.util.readLines

object MetaChaos{
    @JvmStatic
    val subtitles = Seq<String>()

    @JvmStatic
    fun load(){
        val sub = Vars.tree["metachaos/subtitles.txt"].readLines()
        subtitles.addAll(sub)
    }

    @JvmStatic
    fun update(meta: Mods.ModMeta){
        meta.subtitle = subtitles.random()
    }
}
