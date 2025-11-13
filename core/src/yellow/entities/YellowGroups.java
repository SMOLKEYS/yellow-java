package yellow.entities;

import mindustry.entities.*;
import mindustry.gen.*;
import yellow.gen.*;

public class YellowGroups{
    public static EntityGroup<Physicsc> physics;

    public static void init(){
        physics = new EntityGroup<>(Physicsc.class, true, false);
    }

    public static void update(){
        physics.updatePhysics();
    }
}
