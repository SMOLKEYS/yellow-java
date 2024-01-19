package yellow.ui.buttons.dialogs

import arc.scene.ui.Dialog
import mindustry.ui.dialogs.BaseDialog
import yellow.entities.units.entity.YellowUnitEntity

open class BullethellSessionManagerDialog: BaseDialog("@bullethell-session-manager") {

    init {
        addCloseButton()
    }

    fun show(unit: YellowUnitEntity): Dialog{

        cont.clear()

        return super.show()
    }
}