package yellow.internal

import arc.files.*
import arc.struct.*
import mindustry.*

open class MetaChaos{

    val subtitles = Seq<String>()
    val descriptions = Seq<String>()

    init{
        load()
    }
    
    fun List<String>.filterComments(): List<String>{
        return this.filter{ !it.startsWith("#") }
    }
    
    fun Fi.readLines(): List<String>{
        return this.readString().split('\n').filterComments()
    }

    fun load(){
        val meta = Vars.mods.getMod("yellow-java").meta
        subtitles.add(meta.subtitle)
        descriptions.add(meta.description)
        val sub = Vars.tree["metachaos/subtitles.txt"].readLines()
        subtitles.addAll(sub)
        val desc = Vars.tree["metachaos/descriptions.txt"].readLines()
        descriptions.addAll(desc)
    }
}
