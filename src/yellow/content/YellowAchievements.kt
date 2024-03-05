package yellow.content

import mindustry.Vars
import mindustry.content.Blocks
import yellow.game.YAchievement
import yellow.game.YState.dashes
import yellow.game.YState.kills
import yellow.game.YState.menuTime
import yellow.game.achievement.AchievementGameStateCondition
import yellow.util.YellowUtils

@Suppress("unused")
object YellowAchievements {

    @JvmField
    val installed = YAchievement("installed")

    @JvmField
    val menuMan1 = YAchievement("menu-man-1").apply {
        gameStateCondition = AchievementGameStateCondition.menu
        condition = {
            menuTime >= 60*60
        }
    }

    @JvmField
    val menuMan2 = YAchievement("menu-man-2").apply {
        gameStateCondition = AchievementGameStateCondition.menu
        condition = {
            menuTime >= 60*60*30
        }
    }

    @JvmField
    val menuMan3 = YAchievement("menu-man-3").apply {
        gameStateCondition = AchievementGameStateCondition.menu
        condition = {
            menuTime >= 60*60*60
        }
    }

    @JvmField
    val cleanup = YAchievement("cleanup").apply {
        gameStateCondition = AchievementGameStateCondition.sandbox
        condition = {
            kills >= 10000
        }
    }

    @JvmField
    val grass = YAchievement("grass").apply {
        gameStateCondition = AchievementGameStateCondition.ingame
        condition = {
            val yellow = YellowUtils.getActiveYellow(Vars.player.team())

            if(yellow != null) yellow.isIdle() && Vars.world.tile((yellow.x / 8).toInt(), (yellow.y / 8).toInt())!!.floor()!! == Blocks.grass.asFloor() else false
        }
    }

    @JvmField
    val eepy = YAchievement("eepy-yellow").apply {
        gameStateCondition = AchievementGameStateCondition.ingame
        condition = {
            val yellow = YellowUtils.getActiveYellow(Vars.player.team())

            if(yellow != null) yellow.idleTime > 6000f else false
        }
    }

    @JvmField
    val yipee = YAchievement("yipee").apply {
        gameStateCondition = AchievementGameStateCondition.ingame
        condition = {
            dashes >= 1
        }
    }

    @JvmField
    val adios = YAchievement("adios").apply {
        gameStateCondition = AchievementGameStateCondition.ingame
        condition = {
            dashes >= 20
        }
    }

    @JvmField
    val gottaGo = YAchievement("gotta-go").apply {
        gameStateCondition = AchievementGameStateCondition.ingame
        condition = {
            dashes >= 100
        }
    }
}