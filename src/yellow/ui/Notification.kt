package yellow.ui

import arc.scene.ui.layout.*
import arc.struct.*

open class Notification{
    
    var priority: Int
    var title = "Title"
    var message = "Message"
    var table = Table()
    
    /** Creates a Notification with the specified priority level. */
    constructor(priority: NotificationPriority){
        this.priority = priority.level
        instances.add(this)
    }
    
    /** Creates a low-priority Notification. */
    constructor() : super(NotificationPriority.LOW)
    
    
    companion object{
        /** Notification instance list. Any created Notification instances will be added here. Template Notification instances may also be added here. */
        val instances = Seq<Notification>()
        
        /** Notification priority level base. Allows easy deletion of notifications in a certain level. */
        enum class NotificationPriority(val level: Int){
            HIGH(3),
            MEDIUM(2),
            LOW(1)
        }
    }
}
