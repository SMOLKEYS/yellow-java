package yellow.entities.units.entity;

import mindustry.gen.*;
import yellow.entities.units.*;
import yellow.equality.*;

public class CharacterEntity extends UnitEntity{

    private static final int mappingId = EntityMapping.register("character-unit", CharacterEntity::new);

    public CharacterEntity(){
        super();
    }

    public void eradicate(){
        health = 0;
        shield = 0;
        damage(Float.MAX_VALUE);
        super.destroy();
        super.dead(true);
        super.remove();
        Equality.annihilate(this, false, false, null, null);
    }

    @Override
    public CharacterUnitType type(){
        return (CharacterUnitType) super.type();
    }

    @Override
    public int classId(){
        return mappingId;
    }

    @Override
    public void update(){
        super.update();

        if(!type().locationMatch(team())) super.remove();

        Unit impostor = Groups.unit.find(e -> e != this && e.type() == type() && e.team() == team());
        if(impostor instanceof CharacterEntity imp) imp.eradicate();
    }
}
