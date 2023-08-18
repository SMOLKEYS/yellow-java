package yellow.ui

import arc.scene.ui.layout.Table
import arc.struct.Seq
import mindustry.gen.*

open class Notification{

    @JvmField
    var priority: Int
    @JvmField
    var title = "Title"
    @JvmField
    var message = "Message"

    var sound = Sounds.message
    var reminderSound = Sounds.chatMessage

    var table = Table().apply{
        background = Tex.pane
        table{
            it.background = Tex.pane
            it.add(title).grow().update{rl ->
                rl.setText(title)
            }
            it.button(Icon.trashSmall){
                this@Notification.remove()
            }
        }.growX().row()

        pane{
            it.add(message).grow().update{bl ->
                bl.setText(message)
            }.get().setWrap(true)
        }.grow().row()
    }

    private var removed = false
    
    /** Creates a Notification with the specified priority level. */
    constructor(priority: NotificationPriority){
        this.priority = priority.prio //lmao
    }
    
    /** Creates a low-priority Notification. */
    constructor() : this(NotificationPriority.LOW)


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
            LOW(1)
        }
        
        /** Clears all notifications with the specified priority level. If -1 is used, all notifications will be cleared. */
        fun clearNotifications(prio: Int){
            if(prio != -1) instances.clear() else instances.each{ if(it.priority == prio) instances.remove(it) }
        }
    }
}
