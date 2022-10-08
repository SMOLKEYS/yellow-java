package yellow.entities.effect

import arc.graphics.Color
import arc.util.Time
import mindustry.Vars
import mindustry.entities.Effect

fun Effect.atPlayerPos(){
    this.at(Vars.player.x, Vars.player.y)
}

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
