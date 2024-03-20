package yellow.internal;

import arc.struct.*;
import mindustry.*;
import yellow.*;
import yellow.entities.units.entity.*;
import yellow.goodies.vn.*;
import yellow.input.*;
import yellow.util.*;

public class YellowUpdateCore{
    public static Seq<CommonKeyListener<?>> keyListeners = new Seq<>();

    public static void update(){
        YellowUnitEntity.getEntities().each(u -> {
            if((u.dead() || !u.isValid()) && !(u.lives() <= 0)){
                u.health(u.maxHealth());
                u.heal(u.maxHealth());
                u.dead(false);
                YellowUtils.safeSet(u, "added", false);
                u.add();
            }
        });
    }

    public static void updateNoncontent(){
        if(!Vars.headless) keyListeners.each(e -> {
            e.update();
            if(e.canRemove()) e.remove();
        });

        YellowVars.ui.boxfrag.update();
    }
}
