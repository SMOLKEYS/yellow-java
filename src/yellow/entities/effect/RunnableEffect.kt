package yellow.entities.effect

import arc.util.Time
import arc.graphics.Colpr
import mindustry.entities.Effect

/** A [Effect] class that can execute code. */
open class RunnableEffect : Effect(var effect: Effect, var afterEffect: (Float, Float) -> Unit){
    
    var extraDelay: Float = 2.5f
    
    override fun create(x: Float, y: Float, rotation: Float, color: Color, data: Any?){
        if(!shouldCreate()) return
        super.create(x, y, rotation, color, data)
        
        Time.run(effect.lifetime + extraDelay){
            afterEffect(x, y)
        }
    }
}