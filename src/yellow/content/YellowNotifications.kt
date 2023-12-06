package yellow.content

import yellow.game.Notification

object YellowNotifications {

    var hi: Notification? = null

    @JvmStatic
    fun load() {
        hi = Notification("hi")
    }
}
