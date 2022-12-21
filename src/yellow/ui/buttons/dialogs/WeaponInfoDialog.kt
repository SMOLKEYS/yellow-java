package yellow.ui.buttons.dialogs

import arc.*
import arc.graphics.*
import arc.scene.ui.*
import arc.scene.ui.layout.*
import mindustry.graphics.*
import mindustry.ui.dialogs.*
import yellow.internal.util.*
import yellow.type.*

open class WeaponInfoDialog : BaseDialog("Weapon Info"){
    
    init{
        addCloseButton()
    }
    
    
    fun show(weapon: NameableWeapon): Dialog{
        cont.clear()
        
        val info = Table()
        info.margin(10f)
        
        info.table{
            it.image(Core.atlas.drawable("status-disarmed")).size(50f)
            it.add("[accent]${weapon.displayName}[]")
        }.row()
        
        info.add("Description").color(Pal.accent).fillX().padTop(10f).row()
        
        info.add(weapon.description).color(Color.lightGray).fillX().get().setWrap(true)
        info.row()
        
        info.add("General Stats").color(Pal.accent).fillX().padTop(3f).row()
        
        info.add(buildString{
            append("[lightgray]Reload:[] ${weapon.reload / 60f} seconds\n")
            append("[lightgray]X, Y:[] ${weapon.x}, ${weapon.y}\n")
            append("[lightgray]Rotate:[] ${weapon.rotate.yesNo()}\n")
            append("[lightgray]Shoot Cone:[] ${weapon.shootCone} degrees\n")
            append("[lightgray]Base Rotation:[] ${weapon.baseRotation} degrees\n")
            if(weapon.rotate) append("[lightgray]Rotate Speed:[] ${weapon.rotateSpeed} degrees\n")
        })
        
        val paenu = ScrollPane(info)
        cont.add(paenu)
        
        return super.show()
    }
}
