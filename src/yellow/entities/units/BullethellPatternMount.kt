package yellow.entities.units

import mindustry.type.Weapon
import yellow.type.BullethellWeapon

open class BullethellWeaponMount(weapon: Weapon): DisableableWeaponMount(weapon) {

    var remainingTime = 1

    fun setup(){
        remainingTime = (weapon as BullethellWeapon).timespan.random()
    }

    fun update(){
        if(remainingTime > 0){
            remainingTime--
        }
    }
}
