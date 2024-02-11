package yellow.internal;

import arc.struct.*;
import yellow.input.*;
import yellow.world.*;

public class YellowUpdateCore{
    public static Seq<CommonKeyListener<?>> keyListeners = new Seq<>();

    public static void update(){
        BullethellSessionManager.update();

        keyListeners.each(e -> {
            e.update();
            if(e.canRemove()) e.remove();
        });
    }
}
