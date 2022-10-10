package yellow.ai

import mindustry.gen.*
import mindustry.entities.units.*
import yellow.content.YellowUnitTypes

open class YellowFollowerAI : AIController(){
    
    override fun updateMovement(){
        var unitr = null
        
        Groups.unit.each{
            if(it.type == YellowUnitTypes.yellow) unitr = it
        }
        
        if(unit != null){
            moveTo(unit, 12f)
        }
        
        faceMovement()
    }
}