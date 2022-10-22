package yellow.internal

import arc.struct.Seq
import mindustry.Vars

open class MetaChaos{

    val subtitles = Seq<String>()
    val descriptions = Seq<String>()

    init{
        load()
    }

    fun load(){
        val meta = Vars.mods.getMod("yellow-java").meta
        subtitles.add(meta.subtitle)
        descriptions.add(meta.description)
        val sub = Vars.tree["metachaos/subtitles.txt"].readString().split('\n').filter{ !it.startsWith("#") }
        subtitles.addAll(sub)
        val desc = Vars.tree["metachaos/descriptions.txt"].readString().split('\n').filter{ !it.startsWith("#") }
        descriptions.addAll(desc)
    }
}