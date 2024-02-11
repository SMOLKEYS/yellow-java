package yellow.ui.buttons.dialogs

import arc.scene.ui.Dialog
import arc.util.Scaling
import com.github.mnemotechnician.mkui.extensions.dsl.*
import mindustry.gen.Tex
import mindustry.graphics.Pal
import mindustry.ui.dialogs.BaseDialog
import yellow.game.YAchievement

@Suppress("LeakingThis")
open class AchievementListDialog: BaseDialog("Achievements") {
    init {
        addCloseButton()
    }

    override fun show(): Dialog{
        cont.clear()

        cont.scrollPane {
            YAchievement.instances.each {ach ->
                addTable {
                    background = Tex.pane

                    addTable(Tex.pane) {
                        addImage(if(ach.isUnlocked()) ach.icon else ach.iconLocked, Scaling.bounded)
                    }.left().size(140f)

                    addTable {
                        addLabel(if(ach.isUnlocked()) ach.displayName else "[darkgray]???[]").growX().padBottom(4f).row()
                        image().color(Pal.accent).height(5f).growX().row()
                        scrollPane {
                            addLabel(if(ach.isUnlocked()) ach.description else "[darkgray]< locked >[]\n${ach.hint}", wrap = true).grow()
                        }.grow()
                    }.grow().padLeft(5f)
                }.growX().height(170f).row()
            }
        }.grow()

        return super.show()
    }
}