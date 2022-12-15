package yellow.ui.buttons.dialogs

import arc.*
import arc.scene.ui.*
import com.github.mnemotechnician.mkui.extensions.dsl.*
import mindustry.game.*
import mindustry.gen.*
import mindustry.graphics.*
import mindustry.ui.Styles
import mindustry.ui.dialogs.*
import yellow.content.*
import yellow.internal.util.*
import yellow.internal.util.YellowUtils.*
import yellow.type.*

open class FoodDialog : BaseDialog("Food"){

    var selectedItem = YellowItems.stockItem

    init{
        this.addCloseButton()
    }
    
    fun show(team: Team): Dialog{
        cont.clear()
        
        cont.let{
            //unit info (health + armor, sorted by lowest first (provided i can do that)), top-left
            addTable(Tex.pane){
                
            }
            
            //item selection + info (healing, healing type, etc etc), top-right
            addTable(Tex.pane){
                
            }
            
            row()
            
            //unit selection (clicking a unit will make it consume the selected food item), bottom-middle
            addTable(Tex.pane){
                
            }
        }

        return super.show()
    }
}
