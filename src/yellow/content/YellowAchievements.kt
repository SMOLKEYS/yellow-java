package yellow.content

import yellow.game.YState.kills
import yellow.game.YState.menuTime
import yellow.game.achievement.*

@Suppress("unused")
object YellowAchievements {

    @JvmField
    val installed = Achievement("installed")

    @JvmField
    val menuMan1 = Achievement("menu-man-1").apply {
        gameStateCondition = AchievementGameStateCondition.menu
        condition = {
            menuTime >= 60*60
        }
    }

    @JvmField
    val menuMan2 = Achievement("menu-man-2").apply {
        gameStateCondition = AchievementGameStateCondition.menu
        condition = {
            menuTime >= 60*60*30
        }
    }

    @JvmField
    val menuMan3 = Achievement("menu-man-3").apply {
        gameStateCondition = AchievementGameStateCondition.menu
        condition = {
            menuTime >= 60*60*60
        }
    }

    @JvmField
    val cleanup = Achievement("cleanup").apply {
        gameStateCondition = AchievementGameStateCondition.sandbox
        condition = {
            kills >= 10000
        }
    }
}