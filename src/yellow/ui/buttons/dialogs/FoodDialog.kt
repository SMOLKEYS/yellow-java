package yellow.ui.buttons.dialogs

import arc.scene.ui.Dialog
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

        Groups.unit.each{
            if(it.team == team){
                YellowUtils.foodOpts(cont, it, {}, {})
            }
        }

        return super.show()
    }
}