package yellow.ui.buttons.dialogs

import arc.Core
import arc.scene.ui.Dialog
import arc.scene.ui.ScrollPane
import com.github.mnemotechnician.mkui.extensions.dsl.addTable
import com.github.mnemotechnician.mkui.extensions.dsl.scrollPane
import mindustry.game.Team
import mindustry.gen.Groups
import mindustry.ui.dialogs.BaseDialog
import yellow.internal.util.YellowUtils

open class FoodDialog : BaseDialog("Food"){

    init{
        this.addCloseButton()
    }


    fun show(team: Team): Dialog{
        cont.clear()

        cont.scrollPane{ scr: ScrollPane ->
            Groups.unit.each{
                if(it.team == team){
                    YellowUtils.foodOpts(this, it, {}, {})
                    this.row()
                }
            }
            scr.width = Core.graphics.width.toFloat()
        }.size(Core.graphics.width.toFloat(), Core.graphics.height.toFloat())


        return super.show()
    }
}