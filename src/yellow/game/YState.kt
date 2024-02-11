package yellow.game

import arc.Events
import com.github.mnemotechnician.mkui.delegates.setting
import mindustry.Vars
import mindustry.game.EventType.Trigger
import mindustry.game.EventType.UnitBulletDestroyEvent
import yellow.entities.units.entity.YellowUnitEntity

object YState {
    val syn = "yellow-state"

    private var menuTimeJumps = 0
    var menuTime = 0
    var totalMenuTime by setting(0, syn)
    var kills by setting(0, syn)
    var dashes by setting(0, syn)

    fun load(){
        Events.run(Trigger.update){ update() }

        Events.on(UnitBulletDestroyEvent::class.java){
            if(it.bullet.owner() != null && it.bullet.owner() is YellowUnitEntity) kills += 1
        }
    }

    fun update(){
        if(Vars.state.isMenu){
            menuTime += 1
            menuTimeJumps += 1
            if(menuTimeJumps >= 120){
                totalMenuTime += 120
                menuTimeJumps = 0
            }
        }else{
            menuTime = 0
        }

        YAchievement.instances.each { it.update() }
    }
}