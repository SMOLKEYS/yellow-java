package yellow.ui.buttons.dialogs

import arc.*
import arc.scene.ui.*
import com.github.mnemotechnician.mkui.extensions.dsl.*
import mindustry.game.*
import mindustry.gen.*
import mindustry.graphics.*
import mindustry.ui.Styles
import mindustry.ui.dialogs.*
import yellow.internal.util.*
import yellow.internal.util.YellowUtils.*
import yellow.type.*

open class FoodDialog : BaseDialog("Food"){

    init{
        this.addCloseButton()
    }


    fun show(team: Team): Dialog{
        cont.clear()

        //left table - unit info
        cont.addTable(Tex.pane){

        }

        //right table - food selection
        cont.addTable(Tex.pane){

        }

        cont.row()
        //bottom table - unit selection (list? grid? or both as options?)
        cont.addTable(Tex.pane){

        }

        return super.show()
    }
}
