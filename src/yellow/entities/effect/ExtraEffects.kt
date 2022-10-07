package yellow.entities.effect

import arc.util.Time
import arc.graphics.Color
import mindustry.entities.Effect
import mindustry.entities.Effect.EffectContainer

/** A [Effect] class that can execute code. */
open class RunnableEffect(life: Float, consRenderer: (EffectContainer) -> Unit, var afterEffect: (Float, Float) -> Unit) : Effect(life, consRenderer){
    
    var extraDelay: Float = 2.5f
    
    override fun create(x: Float, y: Float, rotation: Float, color: Color, data: Any?){
        if(!shouldCreate()) return
        super.create(x, y, rotation, color, data)
        
        Time.run(this.lifetime + extraDelay){
            afterEffect(x, y)
        }
    }
}

open class InstantRunnableEffect(life: Float, consRenderer: (EffectContainer) -> Unit, var afterEffect: (Float, Float) -> Unit) : Effect(life, consRenderer){
    
    override fun create(x: Float, y: Float, rotation: Float, color: Color, data: Any?){
            if(!shouldCreate()) return
            super.create(x, y, rotation, color, data)
            afterEffect(x, y)
    }
}
