package yellow.ui.buttons.dialogs

import arc.Core
import arc.scene.ui.Dialog
import arc.scene.ui.ScrollPane
import arc.scene.ui.layout.Table
import com.github.mnemotechnician.mkui.extensions.dsl.addTable
import com.github.mnemotechnician.mkui.extensions.dsl.scrollPane
import mindustry.game.Team
import mindustry.gen.Groups
import mindustry.graphics.Pal
import mindustry.ui.dialogs.BaseDialog
import yellow.internal.util.YellowUtils
import yellow.internal.util.YellowUtilsKt
import yellow.type.FoodItem

open class FoodDialog : BaseDialog("Food"){

    init{
        this.addCloseButton()
    }


    fun show(team: Team): Dialog{
        cont.clear()

        cont.scrollPane{ scr: ScrollPane ->
            YellowUtilsKt.seperator(this, Core.graphics.width.toFloat(), 4f, Pal.accent)
            this.row()
            Groups.unit.each{ unit ->
                if(unit.team == team){
                    YellowUtils.foodOpts(this, unit, {}){ itt ->
                        YellowUtils.table(itt, {
                            it.grow()
                        }, { table ->
                            table.pane{ pan ->
                                FoodItem.instances.each{
                                    pan.button(it.nameShort){
                                        if(it.hasThis(team)) it.consume(unit, team)
                                    }.get().label.setWrap(false)
                                    pan.row()
                                }
                            }.grow()
                        })
                    }
                    this.row()
                    YellowUtilsKt.seperator(this, Core.graphics.width.toFloat(), 4f, Pal.accent)
                    this.row()
                }
            }
            scr.width = Core.graphics.width.toFloat()
        }.size(Core.graphics.width.toFloat(), Core.graphics.height.toFloat() / 1.15f)


        return super.show()
    }
}