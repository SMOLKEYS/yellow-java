package yellow.ui.buttons.dialogs

import arc.scene.ui.*
import com.github.mnemotechnician.mkui.extensions.dsl.*
import kotmindy.mindustry.*
import kotmindy.mindustry.world.*
import mindustry.game.*
import mindustry.gen.*
import mindustry.ui.dialogs.*
import yellow.content.*


open class FoodDialog : BaseDialog("Food"){

    var selectedItem = YellowItems.stockItem

    init{
        this.addCloseButton()
    }
    
    fun show(team: Team): Dialog{
        cont.clear()
        
        //unit info (health + armor, sorted by lowest first (provided i can do that)), top-left
        cont.table(Tex.pane){

        }.grow()
            
        //item selection + info (healing, healing type, etc etc), top-right
        cont.table(Tex.pane){
            
        }.grow()
            
        cont.row()
            
        //unit selection (clicking a unit will make it consume the selected food item), bottom-middle
        cont.table(Tex.pane){
            
        }.grow()

        return super.show()
    }
}
