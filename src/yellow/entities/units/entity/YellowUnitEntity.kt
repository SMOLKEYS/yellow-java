package yellow.entities.units.entity

import arc.math.Mathf
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.gen.EntityMapping
import mindustry.gen.UnitEntity
import mindustry.entities.Units
import yellow.entities.units.YellowUnitType
import yellow.game.YellowPermVars

open class YellowUnitEntity: UnitEntity() {

    private var inited = false

    var lives = 0
    var firstDeath = false
    var allowsHealing = false

    fun initVars() {
        inited = true
        lives = type().maxLives
        allowsHealing = Mathf.chance(0.346f)
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
        if(!YellowPermVars.removeAllowed && lives > 1){
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
            YellowPermVars.removeAllowed = true
            remove()
        } else {
            YellowPermVars.removeAllowed = false
        }
        
        //heal surrounding units; normal units gain 10 health, player units gain either no health or a third of their current health
        if(allowsHealing){
            Units.nearby(x, y, 15*8, 15*8){
                if(!it.isPlayer()){
                    if(Mathf.chanceDelta(0.09f)){
                        it.heal(10f)
                    }
                }else{
                    if(Mathf.chanceDelta(0.14f)){
                        it.heal(Mathf.random() * it.health / 3f)
                    }
                }
            }
        }
    }


    override fun write(write: Writes) {
        super.write(write)
        write.bool(inited)
        write.bool(firstDeath)
        write.i(lives)

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
