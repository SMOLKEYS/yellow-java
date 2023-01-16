package yellow.entities.units.entity

import arc.*
import arc.math.*
import arc.math.geom.*
import arc.struct.*
import arc.util.io.*
import kotmindy.mindustry.*
import mindustry.*
import mindustry.content.*
import mindustry.entities.*
import mindustry.entities.units.*
import mindustry.gen.*
import yellow.*
import yellow.entities.units.*
import yellow.game.*
import yellow.game.YEventType.DeathInvalidationEvent

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class YellowUnitEntity: UnitEntity(), Spellcaster{

    //TODO finalizing fields, as adding new ones/removing old ones causes save corruption
    
    private var inited = false
    private var firstDeath = false
    private var franticTeleportTime = 60f
    private var tensionPoints = 0f
    private val everywhere = Vec2()
    
    //turn into private field?
    var lives = 0
    
    var allowsHealing = false
    var panicMode = false
    var panicModeTypeTwo = false

    
    private fun initVars(){
        inited = true
        lives = type().maxLives
        allowsHealing = Mathf.chance(0.346)
        panicMode = Mathf.chance(0.221)
        panicModeTypeTwo = Mathf.chance(0.124)
        entities.add(this)
        
        //inherit from found instance
        findInstance()?.eachMountAs<DisableableWeaponMount>{ins ->
            eachMountAs<DisableableWeaponMount>{you ->
                you.enabled = ins.enabled
            }
        }
    }
    
    private fun invalidateVars(){
        lives = 0
        allowsHealing = false
        panicMode = false
        panicModeTypeTwo = false
        dead = true
        health = 0f
        shield = 0f
        entities.remove(this)
    }
    
    private fun invalidateDeath(){
        lives -= 1
        health = type.health
        dead = false
        elevation = 1f

        if((lives == type().maxLives - 1) && !firstDeath) {
            shield = Mathf.random(1000f, 8000f)
            firstDeath = true
        }
        
        if(isPlayer){
            Vars.ui.showInfoFade("$lives left!")
        }
        
        //sorry, but yellow ain't going down to the void
        if(outOfWorldBounds()){
            if(team.data().cores.isEmpty){
                x = Mathf.random(Vars.world.width()) * 8f
                y = Mathf.random(Vars.world.height()) * 8f
            }else{
                val core = team.data().cores[0]
                x = core.x
                y = core.y
            }
        }else{
            x += Mathf.range(25f * 8f)
            y += Mathf.range(25f * 8f)
        }
        
        //fire the death invalidation event after everything
        Events.fire(DeathInvalidationEvent(this))
    }
    
    private fun destroyFull(){
        invalidateVars()
        super.destroy()
    }
    
    private fun removeFull(){
        invalidateVars()
        super.remove()
    }
    
    fun outOfWorldBounds(): Boolean{
        return x > Vars.world.width() * 8f || x < 0f || y > Vars.world.height() * 8f || y < 0f
    }


    fun forceKill() {
        while(lives > 0) {
            kill()
        }
    }
    
    inline fun <reified T : WeaponMount> eachMountAs(cons: (T) -> Unit){
        mounts().forEach{
            if(it is T) cons(it)
        }
    }

    override fun type(): YellowUnitType {
        return type as YellowUnitType
    }
    
    override fun kill() {
        destroy() //just call destroy(), no point in waiting
    }
    

    override fun destroy(){
        
        if(lives > 1) {
            invalidateDeath()
            return
        }
        
        destroyFull()
    }

    override fun remove(){
    
        if(!YellowPermVars.removeAllowed && lives > 1){
            return
        }


        removeFull()
    }
    
    
    //just call the damage(float) method
    //disregards withEffect entirely
    override fun damage(amount: Float){
        super.damage(amount)
    }
    
    override fun damage(amount: Float, withEffect: Boolean){
        damage(amount)
    }
    
    override fun damagePierce(amount: Float){
        damage(amount)
    }
    
    override fun damagePierce(amount: Float, withEffect: Boolean){
        damage(amount)
    }

    override fun update() {
        super.update()

        if(!inited) {
            initVars()
        }

        spawnedByCore = false

        if(team.data().countType(type) > 1) {
            YellowPermVars.removeAllowed = true
            removeFull()
        } else {
            YellowPermVars.removeAllowed = false
        }
        
        
        //heal surrounding units; normal units gain 10 health, player units gain either no health or a third of their current health
        //very picky woman i must say :trollar:
        if(allowsHealing){
            Units.nearby(x, y, 15f*8f, 15f*8f){a: MUnit ->
                if(a.team == team){
                    if(!a.isPlayer){
                        if(Mathf.chanceDelta(0.09)){
                            a.heal(10f)
                        }
                    }else{
                        if(Mathf.chanceDelta(0.14)){
                            a.heal(Mathf.random() * a.health.div(3f))
                        }
                    }
                }
            }
        }
        
        
        //teleport everywhere and start firing all weapons on last life for a second if panic mode is enabled
        if(panicMode && lives == 1 && franticTeleportTime > 0f){
            everywhere.set(Mathf.random(Vars.world.width()) * 8f, Mathf.random(Vars.world.height()) * 8f)
            x = everywhere.x
            y = everywhere.y

            val mountus = mounts.random()

            mountus.shoot = true
            mountus.weapon.update(this, mountus)
            
            //if type two panic mode is enabled, start dropping quad bombs
            if(panicModeTypeTwo){
                UnitTypes.quad.weapons[0].bullet.create(this, x, y, 0f)
            }
            
            franticTeleportTime--
        }
    }
    
    override fun getTensionPoints() = tensionPoints
    
    override fun setTensionPoints(set: Float){
        tensionPoints = set
    }
    
    override fun addTensionPoints(amount: Float){
        tensionPoints += amount
    }
    
    override fun removeTensionPoints(amount: Float){
        tensionPoints -= amount
    }
    
    override fun toString() = if(isValid) "YellowUnitEntity#$id:${type.name}" else "(invalid) YellowUnitEntity#$id:${type.name}"


    override fun write(write: Writes){
        super.write(write)
        write.bool(inited)
        write.bool(firstDeath)
        write.bool(allowsHealing)
        write.bool(panicMode)
        write.bool(panicModeTypeTwo)
        write.i(lives)
        write.f(tensionPoints)
        write.f(franticTeleportTime)
        
        eachMountAs<DisableableWeaponMount>{
            it.write(write)
        }
        
    }

    override fun read(read: Reads){
        super.read(read)
        inited = read.bool()
        firstDeath = read.bool()
        allowsHealing = read.bool()
        panicMode = read.bool()
        panicModeTypeTwo = read.bool()
        lives = read.i()
        tensionPoints = read.f()
        franticTeleportTime = read.f()
        
        eachMountAs<DisableableWeaponMount>{
            it.read(read)
        }
        
    }
    
    override fun classId() = mappingId

    companion object{
        val mappingId = EntityMapping.register("yellow-unit", ::YellowUnitEntity)
        
        @JvmStatic
        val entities = Seq<YellowUnitEntity>()
        
        fun findInstance(): YellowUnitEntity?{
            Groups.unit.each{
                if(it is YellowUnitEntity) return@findInstance it
            }
            return null
        }
    }
}
