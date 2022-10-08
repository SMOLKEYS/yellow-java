package yellow.entities.units.entity

import arc.math.Mathf
import arc.util.Time
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.content.Fx
import mindustry.gen.EntityMapping
import mindustry.gen.UnitEntity
import yellow.entities.units.GhostUnitType

open class GhostUnitEntity: UnitEntity() {
    var ghostLifetime = 0f
    var despawnEffect = Fx.none

    private var inited = false

    fun lifetimef(): Float {
        return ghostLifetime / (type as GhostUnitType).ghostLifetime
    }

    fun clampLifetime() {
        ghostLifetime = Mathf.clamp(ghostLifetime, 0f, (type as GhostUnitType).ghostLifetime)
    }

    fun initVars() {
        inited = true
        ghostLifetime = (type as GhostUnitType).ghostLifetime
        despawnEffect = (type as GhostUnitType).despawnEffect
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
