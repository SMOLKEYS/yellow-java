package yellow.entities.effect

import arc.graphics.Color
import mindustry.entities.Effect

open class RandomEffect(var effects: Array<Effect>) : Effect(0f, {}){
    
    override fun create(x: Float, y: Float, rotation: Float, color: Color, data: Any?){
        if(!shouldCreate()) return
        effects.random().create(x, y, rotation, color, data)
    }
}