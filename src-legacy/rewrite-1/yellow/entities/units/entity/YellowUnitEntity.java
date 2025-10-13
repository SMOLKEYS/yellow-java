package yellow.entities.units.entity;

import mindustry.gen.*;

public class YellowUnitEntity extends MagicSpecialistEntity{

    private static final int mappingId = EntityMapping.register("yellow-unit", YellowUnitEntity::new);

    public YellowUnitEntity(){
        super();
    }

    @Override
    public int classId(){
        return mappingId;
    }

    @Override
    public boolean spawnedByCore(){
        spawnedByCore = false;
        return false;
    }

    @Override
    public void spawnedByCore(boolean spawnedByCore){
        super.spawnedByCore(false); //no
    }

    @Override
    public int cap(){
        return 1;
    }

    @Override
    public void removeLife(){
        super.removeLife();
    }

    @Override
    public YellowUnitType type(){
        return (YellowUnitType) super.type();
    }

    @Override
    public void remove(){
        if(hasLives()) return;
        super.remove();
    }

    @Override
    public void kill(){
        super.destroy();
    }
}
