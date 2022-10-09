package yellow.entities.effect

import arc.graphics.Color
import arc.util.Log
import arc.util.Time
import mindustry.Vars
import mindustry.entities.Effect

fun Effect.atPlayerPos(){
    this.at(Vars.player.x, Vars.player.y)
}

open class RunnableEffect(life: Float, consRenderer: (EffectContainer) -> Unit, var afterEffect: (Float, Float) -> Unit) : Effect(life, consRenderer){
    
    var extraDelay: Float = 0.5f
    
    override fun create(x: Float, y: Float, rotation: Float, color: Color, data: Any?){
        if(!shouldCreate()) return
        super.create(x, y, rotation, color, data)
        
        Time.run(lifetime + extraDelay){
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

open class TimedRunnableEffect(life: Float, consRenderer: (EffectContainer) -> Unit, var at: Float, var afterAt: (Float, Float) -> Unit) : Effect(life, consRenderer){

    init{
        if(at > lifetime) Log.warn("at > lifetime on effect instance $this. Use RunnableEffect and its delay field instead. afterAt will not be ran.")
        if(at == 0f) Log.warn("at == 0 on effect instance $this. Use InstantRunnableEffect instead.")
    }

    override fun create(x: Float, y: Float, rotation: Float, color: Color?, data: Any?) {
        if(!shouldCreate()) return
        super.create(x, y, rotation, color, data)

        if(at > lifetime) return
        Time.run(at){
            afterAt(x, y)
        }
    }
}