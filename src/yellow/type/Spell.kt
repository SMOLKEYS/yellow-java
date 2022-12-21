package yellow.type

import kotmindy.mindustry.*
import mindustry.gen.*
import yellow.game.*
import yellow.internal.util.*

/** The root spell class. Comes with generic healing fields. */
open class Spell(){
    var healing = 10f
    var healingByFract = 0f
    var cooldown = 60f
    var cost = 27f
    
    var groupHeal = false
    var healWithFract = false
    
    var predicate: (MUnit) -> Boolean = { it.healthFract() < 0.2f }
    
    fun cast(user: Spellcaster){
        if((user.tensionPoints < cost) || (user !is MUnit)) return
        var healedTargets = 0
        
        Groups.unit.each{
            if(!groupHeal && healedTargets > 0) return@each
            if(predicate(it) && (it.team == (user as MUnit).team)){
                if(healWithFract) it.healFract(healingByFract) else it.heal(healing)
                healedTargets++
            }
        }
        
        if(healedTargets != 0) user.removeTensionPoints(cost)
    }
}
