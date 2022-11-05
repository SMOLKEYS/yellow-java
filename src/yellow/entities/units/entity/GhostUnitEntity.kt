package yellow.entities.units.entity

import arc.math.*
import arc.util.*
import arc.util.io.*
import mindustry.content.*
import mindustry.entities.*
import mindustry.gen.*
import yellow.entities.units.*

open class GhostUnitEntity: UnitEntity() {
    var ghostLifetime = 0f
    var despawnEffect: Effect = Fx.none

    private var inited = false

    fun lifetimef(): Float {
        return ghostLifetime / (type as GhostUnitType).ghostLifetime
    }

    fun clampLifetime() {
        ghostLifetime = Mathf.clamp(ghostLifetime, 0f, (type as GhostUnitType).ghostLifetime)
    }

    private fun initVars() {
        inited = true
        ghostLifetime = (type as GhostUnitType).ghostLifetime + Mathf.random(60f)
        despawnEffect = (type as GhostUnitType).despawnEffect
    }
    
    override fun kill(){
        Fx.unitDespawn.at(x, y, 0f, this)
        remove()
    }
    
    override fun destroy(){
        Fx.unitDespawn.at(x, y, 0f, this)
        remove()
    }

    override fun update() {
        super.update()

        if(!inited) {
            initVars()
        }

        ghostLifetime -= Time.delta
        clampLifetime()

        if(ghostLifetime <= 0f) {
            val ty = (type as GhostUnitType)
            remove()
            ty.despawnEffect.at(x + ty.despawnEffectOffset.x, y + ty.despawnEffectOffset.y)
        }
    }

    override fun cap(): Int {
        return count() + 1
    }

    override fun write(write: Writes) {
        super.write(write)
        write.f(ghostLifetime)
        write.bool(inited)
    }

    override fun read(read: Reads) {
        super.read(read)
        ghostLifetime = read.f()
        inited = read.bool()
    }

    override fun classId() = mappingId

    companion object {
        val mappingId = EntityMapping.register("ion-ghost-unit", ::GhostUnitEntity)
    }
}
