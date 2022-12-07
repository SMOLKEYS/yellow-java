package yellow.type

import arc.func.*
import mindustry.gen.*
import yellow.game.*
import yellow.internal.util.*

/** The root spell class. Comes with generic healing fields. */
open class Spell(){
    var healing = 10f
    var healingByFract = 0f
    var cooldown = 60f
    var cost = 27
    
    var groupHeal = false
    var healWithFract = false
    
    var predicate: (MUnit) -> Boolean = {
        val kr = it.healthFract() < 0.2f
        return kr
    }
    
    fun cast(user: Spellcaster){
        if((user.getTensionPoints() < cost) || (user !is MUnit)) return
        var healedTargets = 0
        
        Groups.unit.each{
            if(!groupHeal && healedTargets) return@each
            if(predicate(it) && (it.team == user.team)){
                if(healWithFract) it.healFract(healingByFract) else it.heal(healing)
                healedTargets++
            }
        }
        
        if(healedTargets != 0) user.removeTensionPoints(cost)
    }
}
