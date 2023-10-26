package yellow.game.achievement

import arc.Core
import arc.scene.style.Drawable
import arc.struct.Seq
import com.github.mnemotechnician.mkui.delegates.setting
import mindustry.Vars
import mindustry.gen.*
import yellow.internal.Namec

open class Achievement(val name: String): Namec {

    private var unlocked by setting(false, "yellow-achievement-$name-")

    var parent: Achievement? = null
    var requiredAchievements: Array<Achievement>? = null
    var displayName: String
    var description: String
    var icon: Drawable = Icon.lockOpen
    var iconLocked: Drawable = Icon.lock
    var gameStateCondition = AchievementGameStateCondition.any
    var condition: () -> Boolean = {true}
    var unlockSound = Sounds.message
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
        if(unlocked) return
        unlockInvocation()
    }

    fun lock(){
        unlocked = false
    }

    fun isUnlocked() = unlocked

    fun update(){
        if(!isUnlocked() && gameStateCondition.condition[Vars.state] && condition()) {
            if(requiredAchievements != null) {
                if(requiredAchievements?.all {it.unlocked} == true) unlockInvocation()
            } else unlockInvocation()
        }
    }

    private fun unlockInvocation(){
        unlocked = true
        unlockSound.play()
        onUnlock()
    }

    override fun nameLocalized() = Core.bundle["achievement.$name.name"]

    override fun descriptionLocalized() = Core.bundle["achievement.$name.description"]

    companion object{
        @JvmStatic
        val instances = Seq<Achievement>()
    }
}