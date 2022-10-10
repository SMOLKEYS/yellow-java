package yellow.ai

import mindustry.gen.*
import mindustry.entities.units.*
import yellow.content.YellowUnitTypes

open class YellowFollowerAI : AIController(){
    
    override fun updateMovement(){
        
        Groups.unit.each{
            if(it.type == YellowUnitTypes.yellow){
                unit = it
            }else{
                unit = null
            }
        }
        
        if(unit != null){
            moveTo(unit, 12f)
        }
        
        faceMovement()
    }
}
