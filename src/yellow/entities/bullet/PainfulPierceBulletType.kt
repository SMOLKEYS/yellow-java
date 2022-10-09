package yellow.entities.bullet

import mindustry.gen.Bullet
import mindustry.entities.bullet.BulletType

open class PainfulPierceBulletType(speed: Float, damage: Float, var damageBenefitPerPierce: Float) : BulletType(speed, damage){
    
    init{
        pierce = true
        pierceBuilding = true
        pierceCap = -1
    }bror
    
    override fun hit(b: Bullet){
        super.hit(b)
        
        b.damage += damageBenefitPerPierce
    }
}
