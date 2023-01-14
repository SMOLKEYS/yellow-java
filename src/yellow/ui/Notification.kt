package yellow.ui

import arc.scene.ui.layout.*
import arc.struct.*
import com.github.mnemotechnician.mkui.extensions.dsl.scrollPane
import mindustry.gen.Icon
import mindustry.gen.Sounds
import mindustry.gen.Tex

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
        this.priority = priority.level
    }
    
    /** Creates a low-priority Notification. */
    constructor() : this(NotificationPriority.LOW)


    fun remove(){
        if(!instances.contains(this)) return
        instances.remove(this)
        removed = true
    }

    fun add(){
        sound.play()
        if(instances.contains(this)){
            reminderSound.play(reminderSoundVolume)
            return
        }
        instances.add(this)
        removed = false
    }

    fun getRemoved() = removed
    
    companion object{
        /** Notification instance list. Any created Notification instances will be added here. Template Notification instances may also be added or readded here. */
        val instances = Seq<Notification>()

        var reminderSoundVolume = 40f
        
        /** Notification priority level base. Allows easy deletion of notifications in a certain level. */
        enum class NotificationPriority(val level: Int){
            HIGH(3),
            MEDIUM(2),
            LOW(1)
        }
    }
}
