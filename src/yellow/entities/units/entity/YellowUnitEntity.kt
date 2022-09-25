package yellow.entities.units.entity

import arc.util.io.Reads
import arc.util.io.Writes
import yellow.game.PermVars
import yellow.entities.units.YellowUnitType
import mindustry.gen.EntityMapping
import mindustry.gen.UnitEntity

open class YellowUnitEntity: UnitEntity() {

    private var inited = false

    var lives = 0
    var firstDeath = false

    fun initVars() {
        inited = true
        lives = type().maxLives
        Utils.logD("initalized $this")
    }

    fun invalidateDeath() {
        lives -= 1
        health = type.health
        dead = false
        elevation = 1f

        if((lives == type().maxLives - 1) && !firstDeath) {
            shield = 6780f
            firstDeath = true
        }
    }

    fun forceKill() {
        while(lives > 0) {
            kill()
        }
    }

    override fun type(): YellowUnitType {
        return type as YellowUnitType
    }

    override fun kill() {
        destroy() //just call destroy(), no point in waiting
    }

    override fun destroy() {
        if(lives > 1) {
            invalidateDeath()
            return
        }
        
        super.destroy()
    }

    override fun remove() {
        if(!PermVars.removeAllowed && lives > 1){
            return
        }

        super.remove()
    }

    override fun update() {
        super.update()

        if(!inited) {
            initVars()
        }

        spawnedByCore = true

        if(team.data().countType(type) > 1) {
            PermVars.removeAllowed = true
            remove()
        } else {
            PermVars.removeAllowed = false
        }
    }


    override fun write(write: Writes) {
        super.write(write)
        write.bool(inited)
        write.bool(firstDeath)
        write.i(lives)

        Utils.logD("WRITE: inited ($inited), firstDeath ($firstDeath), lives ($lives)")
        /*
        mounts.forEach{
            val e = it as DisableableWeaponMount
            write.bool(e.enabled)
        }
        */
    }

    override fun read(read: Reads) {
        super.read(read)
        inited = read.bool()
        firstDeath = read.bool()
        lives = read.i()
        /*
        mounts.forEach{
            val e = it as DisableableWeaponMount
            e.enabled = read.bool()
        }
        */
    }

    override fun classId() = mappingId

    companion object {
        val mappingId = EntityMapping.register("ion-yellow-unit", ::YellowUnitEntity)
    }
}