package yellow.ui.buttons.dialogs

import arc.Core
import arc.graphics.Color
import arc.scene.ui.*
import arc.scene.ui.layout.Table
import arc.struct.ObjectMap
import arc.struct.ObjectSet
import arc.struct.Seq
import mindustry.graphics.Pal
import mindustry.ui.dialogs.BaseDialog
import yellow.internal.util.yesNo
import yellow.type.NameableWeapon

open class WeaponInfoDialog : BaseDialog("Weapon Info"){
    
    init{
        addCloseButton()
    }

    private val cache = ObjectMap<NameableWeapon, ScrollPane>() //use caching to reduce object initialization

    fun invalidateCache() = cache.clear()
    
    fun show(weapon: NameableWeapon): Dialog{
        cont.clear()

        if(cache.containsKey(weapon)){
            cont.add(cache[weapon])
            return super.show()
        }
        
        val info = Table()
        info.margin(10f)
        
        info.table{
            it.image(Core.atlas.drawable("status-disarmed")).size(50f)
            it.add("[accent]${weapon.nameLocalized()}[]")
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
            if(!weapon.rotate) append("[lightgray]Base Rotation:[] ${weapon.baseRotation} degrees\n")
            if(weapon.rotate) append("[lightgray]Rotate Speed:[] ${weapon.rotateSpeed} degrees\n")
        })
        
        val paenu = ScrollPane(info)
        cont.add(paenu)
        
        return super.show()
    }
}
