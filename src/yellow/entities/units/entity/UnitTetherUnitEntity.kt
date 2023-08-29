package yellow.entities.units.entity

import arc.util.io.*
import mindustry.gen.UnitEntity
import mindustry.io.TypeIO

open class UnitTetherUnitEntity: UnitEntity(){
    var tether: UnitEntity? = null


    fun tether(unit: UnitEntity?){
        tether = unit
    }

    override fun update(){
        super.update()

        //may not exist when
        // 1. tether is dead
        // 2. tether is null
        // 3. tether is itself
        if(tether!!.dead() || tether == null || tether == this) destroy()
    }

    override fun kill() = destroy() //destroy immediately

    override fun cap() = -1

    override fun write(write: Writes){
        super.write(write)
        TypeIO.writeUnit(write, tether)
    }

    override fun read(read: Reads) {
        super.read(read)
        TypeIO.readUnit(read)
    }
}