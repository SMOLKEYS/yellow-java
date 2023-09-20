package yellow.internal

import arc.files.Fi
import arc.struct.Seq
import mindustry.Vars
import mindustry.mod.Mods

fun List<String>.filterComments(): List<String> = this.filter {it.isBlank() || !it.startsWith("#") || !it.startsWith("--") || !it.startsWith("//")}

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
