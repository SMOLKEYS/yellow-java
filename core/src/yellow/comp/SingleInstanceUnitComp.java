package yellow.comp;

import ent.anno.Annotations.*;
import mindustry.game.*;
import mindustry.gen.*;
import yellow.gen.*;

@EntityComponent
@EntityDef({SingleInstanceUnitc.class, Unitc.class})
abstract class SingleInstanceUnitComp implements Unitc{
    @Import Team team;

    public boolean isClone;

    @Override
    public void update(){
        Groups.unit.each(u -> u != self(), u -> {
            if(u instanceof SingleInstanceUnitc s && u.team == team){
                s.isClone(true);
                s.remove();
            }
        });
    }
}
