package yellow.game

import arc.*
import arc.scene.ui.layout.Table
import arc.struct.Seq
import com.github.mnemotechnician.mkui.extensions.dsl.*
import mindustry.gen.*
import mindustry.graphics.Pal
import yellow.game.YEventType.NotificationCallEvent

open class Notification(var name: String){

    @JvmField
    var priority: Int = -1
    @JvmField
    var title: String = "<notification generic title>"
    @JvmField
    var message: String = "generic notification message."

    var sound = Sounds.message
    var reminderSound = Sounds.chatMessage

    var table = Table().apply{
        background = Tex.pane
        addTable {
            background = Tex.pane
            name = "titletable: $title"

            addLabel({ title }, wrap = false).growX()
            imageButton(Icon.trashSmall.tint(Pal.remove)){
                this@Notification.remove()
            }.right()
        }.growX().row()

        scrollPane {
            addLabel({ message }, wrap = true).grow()
        }.grow().row()
    }

    private var removed = true

    constructor(name: String, priority: NotificationPriority): this(name){
        this.priority = priority.prio
    }

    fun remove(){
        if(!instances.contains(this)) return
        instances.remove(this)
        removed = true
    }

    fun add(){
        if(instances.contains(this)){
            reminderSound.play(reminderSoundVolume)
            return
        }else{
            sound.play()
        }
        instances.add(this)
        Events.fire(NotificationCallEvent(this))
        removed = false
    }

    fun getRemoved() = removed

    companion object{
        /** Notification instance list. Any created Notification instances will be added here. Notification instances may also be readded here. */
        val instances = Seq<Notification>()

        var reminderSoundVolume = 40f

        /** Notification priority level base. Allows easy deletion of notifications in a certain level. */
        enum class NotificationPriority(val prio: Int){
            HIGH(3),
            MEDIUM(2),
            LOW(1),
            ALL(-1)
        }

        /** Clears all notifications with the specified priority level. If -1 is used, all notifications will be cleared. */
        fun clearNotifications(prio: Int){
            instances.each{ if(prio == -1) it.remove() else if(it.priority == prio) it.remove() }
        }

        fun clearNotifications(priority: NotificationPriority){
            clearNotifications(priority.prio)
        }
    }
}