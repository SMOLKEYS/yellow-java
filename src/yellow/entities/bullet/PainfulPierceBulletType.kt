package yellow.entities.bullet

import arc.util.Log
import mindustry.gen.Bullet
import mindustry.entities.bullet.BulletType

open class PainfulPierceBulletType(speed: Float, damage: Float, var damageBenefitPerPierce: Float) : BulletType(speed, damage){
    
    init{
        pierce = true
        pierceBuilding = true
        pierceCap = -1
    }
    
    override fun hit(b: Bullet){
        super.hit(b)
        
        Log.info("${b.damage} original")
        b.damage += damageBenefitPerPierce
        Log.info("${b.damage} new")
    }
}
