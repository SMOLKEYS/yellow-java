package yellow.internal

import arc.files.Fi
import arc.struct.Seq
import mindustry.Vars
import mindustry.mod.Mod
import mindustry.mod.Mods

object MetaChaos{
    @JvmStatic
    val subtitles = Seq<String>()
    @JvmStatic
    val descriptions = Seq<String>()
    
    fun List<String>.filterComments(): List<String>{
        return this.filter{ !it.startsWith("#") }
    }
    
    fun Fi.readLines(): List<String>{
        return this.readString().split('\n').filterComments()
    }

    @JvmStatic
    fun load(){
        val meta = Vars.mods.getMod("yellow-java").meta
        subtitles.add(meta.subtitle)
        descriptions.add(meta.description)
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
