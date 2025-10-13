package yellow.comp;

import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.game.*;
import mindustry.gen.*;
import yellow.gen.*;

@EntityComponent
@EntityDef({SingleInstanceUnitc.class, Unitc.class})
abstract class SingleInstanceUnitComp implements Unitc{
    @Import Team team;

    boolean isClone;

    @Override
    public void update(){
        Groups.unit.each(u -> u != self(), u -> {
            if(u instanceof SingleInstanceUnitc s && u.team == team){
                s.isClone(true);
                s.remove();
            }
        });
    }

    @Override
    public void read(Reads read){
        isClone = read.bool();
    }

    @Override
    public void write(Writes write){
        write.bool(isClone);
    }
}
