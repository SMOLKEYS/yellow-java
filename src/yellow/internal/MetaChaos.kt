package yellow.internal

import arc.Core
import arc.files.Fi
import arc.struct.Seq
import mindustry.Vars
import mindustry.mod.Mods
import yellow.YellowPermVars

fun List<String>.filterComments(): List<String>{
    return this.filter{ !it.startsWith("#") }
}

fun Fi.readLines(): List<String>{
    return this.readString().split('\n').filterComments()
}

object MetaChaos{
    @JvmStatic
    val subtitles = Seq<String>()
    @JvmStatic
    val descriptions = Seq<String>()


    @JvmStatic
    fun load(){
        val sub = Vars.tree["metachaos/subtitles.txt"].readLines()
        subtitles.addAll(sub)
        val desc = Vars.tree["metachaos/descriptions.txt"].readLines()
        descriptions.addAll(desc)
    }

    @JvmStatic
    fun update(meta: Mods.ModMeta){
        meta.subtitle = subtitles.random()
        meta.description = descriptions.random()
    }
}
