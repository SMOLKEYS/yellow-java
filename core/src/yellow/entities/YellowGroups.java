package yellow.entities;

import mindustry.entities.*;
import yellow.gen.*;

public class YellowGroups{
    public static EntityGroup<ItemEntityc> item;

    public static void init(){
        item = new EntityGroup<>(ItemEntityc.class, true, false);
    }

    public static void update(){
        item.updatePhysics();
    }
}
