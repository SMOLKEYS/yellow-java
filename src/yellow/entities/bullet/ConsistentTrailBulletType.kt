package yellow.entities.bullet

import arc.util.*
import mindustry.entities.bullet.*
import mindustry.gen.*
import yellow.internal.util.*

open class ConsistentTrailBulletType(speed: Float, damage: Float) : BulletType(speed, damage){

    var effectSpacing = 15f

    init{
        trailChance = 0f
    }

    override fun update(b: Bullet?) {
        super.update(b)

        if(b != null){
            if(Time.time ins effectSpacing) trailEffect.at(b, b.rotation())
        }
    }
}