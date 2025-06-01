package yellow.entities.units.entity;

import mindustry.gen.*;
import yellow.entities.units.*;
import yellow.equality.*;

public class SingleInstanceEntity extends WeaponMasterEntity{

    private static final int mappingId = EntityMapping.register("single-instance-unit", SingleInstanceEntity::new);

    public SingleInstanceEntity(){
        super();
    }

    public void eradicate(){
        health = 0;
        shield = 0;
        damage(Float.MAX_VALUE);
        super.destroy();
        super.dead(true);
        super.remove();
        EqualityDamage.groupWipe(this);
        EqualityDamage.annihilate(this, false, false, null, null);
    }

    @Override
    public SingleInstanceUnitType type(){
        return (SingleInstanceUnitType) super.type();
    }

    @Override
    public int classId(){
        return mappingId;
    }

    @Override
    public void update(){
        super.update();

        Unit impostor = Groups.unit.find(e -> e != this && e.type() == type() && e.team() == team());
        if(impostor instanceof SingleInstanceEntity imp) imp.eradicate();
    }
}
