package yellow.comp;

import arc.math.geom.*;
import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.gen.*;
import yellow.entities.*;
import yellow.gen.*;

/** Generic component class for an entity that has a hitbox, position/rotation/velocity data, and a collision body. */
@EntityComponent
@EntityDef({PhysicsEntityc.class, Hitboxc.class, Drawc.class, Posc.class, Velc.class, Rotc.class, Physicsc.class})
abstract class PhysicsEntityComp implements Hitboxc, Drawc, Posc, Velc, Rotc, Physicsc{

    @Import float drag, hitSize;

    @Override
    public void add(){
        YellowGroups.physics.add(this);
    }

    @Override
    public void remove(){
        YellowGroups.physics.remove(this);
    }

    @Override
    public void read(Reads read){
        drag = read.f();
        hitSize = read.f();
    }

    @Override
    public void write(Writes write){
        write.f(drag);
        write.f(hitSize);
    }
}
