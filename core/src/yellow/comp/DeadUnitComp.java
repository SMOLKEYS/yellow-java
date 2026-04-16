package yellow.comp;

import ent.anno.Annotations.*;
import mindustry.type.*;
import yellow.gen.*;

@EntityComponent
@EntityDef({DeadUnitc.class, PhysicsEntityc.class})
abstract class DeadUnitComp implements PhysicsEntityc{
    public UnitType type;
}
