package yellow.entities.bullet

import arc.util.Log
import mindustry.gen.Bullet
import mindustry.gen.Hitboxc
import mindustry.entities.bullet.BulletType

open class PainfulPierceBulletType(speed: Float, damage: Float, var damageBenefitPerPierce: Float) : BulletType(speed, damage){
    
    init{
        pierce = true
        pierceBuilding = true
        pierceCap = -1
    }
    
    override fun hit(b: Bullet){
        super.hit(b)
        
        b.damage += damageBenefitPerPierce

    }
    
    override fun hitEntity(b: Bullet, entity: Hitboxc, health: Float){
        super.hitEntity(b, entity, health)
        
        b.damage += damageBenefitPerPierce
    }
}
