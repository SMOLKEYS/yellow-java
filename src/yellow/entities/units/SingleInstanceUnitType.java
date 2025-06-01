package yellow.entities.units;

import mindustry.content.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;
import yellow.entities.units.entity.*;

public class SingleInstanceUnitType extends UnitType{


    public SingleInstanceUnitType(String name){
        super(name);
        constructor = SingleInstanceEntity::new;
        allowedInPayloads = false;
    }

    @Override
    public Unit spawn(Team team, float x, float y){
        if(exists(team)){
            return UnitTypes.flare.spawn(team, x, y);
        }
        return super.spawn(team, x, y);
    }

    @Override
    public Unit create(Team team){
        if(exists(team)){
            return UnitTypes.flare.create(team);
        }
        return super.create(team);
    }

    public boolean exists(Team team){
        return Groups.unit.find(u -> u.type() == this && u.team == team) != null;
    }
}
