package yellow.game

import arc.*
import arc.audio.Sound
import arc.scene.style.Drawable
import arc.struct.Seq
import com.github.mnemotechnician.mkui.delegates.setting
import mindustry.Vars
import mindustry.gen.*
import yellow.game.YEventType.AchievementUnlockEvent
import yellow.game.achievement.AchievementGameStateCondition
import yellow.internal.Namec

@Suppress("MemberVisibilityCanBePrivate")
open class Achievement(var name: String): Namec {

    private var unlocked by setting(false, "yellow-achievement-$name-")
    protected var readyToUnlock by setting(false, "yellow-achievement-$name-")

    var parent: Achievement? = null
    var requiredAchievements: Array<Achievement>? = null
    var displayName: String
    var description: String
    var icon: Drawable = Icon.lockOpen
    var iconLocked: Drawable = Icon.lock
    var gameStateCondition: AchievementGameStateCondition = AchievementGameStateCondition.any
    var condition: () -> Boolean = {true}
    var unlockSound: Sound = Sounds.message
    var onUnlock: () -> Unit = {}


    constructor(name: String, parent: Achievement?): this(name) {
        this.parent = parent
    }

    init {
        displayName = this.nameLocalized()
        description = this.descriptionLocalized()

        instances.add(this)
    }

    fun unlock(){
        if(readyToUnlock) return
        unlockInvocation()
    }

    fun lock(){
        unlocked = false
    }

    fun isUnlocked() = unlocked

    fun update(){
        readyToUnlock = if(requiredAchievements != null){
            requiredAchievements!!.all {it.unlocked} && gameStateCondition.condition[Vars.state] && condition()
        }else{
            gameStateCondition.condition[Vars.state] && condition()
        }
    }

    protected fun unlockInvocation(){
        unlocked = true
        unlockSound.play()
        Events.fire(AchievementUnlockEvent(this))
        onUnlock()
    }

    override fun nameLocalized() = Core.bundle["achievement.$name.name"]

    override fun descriptionLocalized() = Core.bundle["achievement.$name.description"]

    companion object{
        @JvmStatic
        val instances = Seq<Achievement>()
    }
}