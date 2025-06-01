package yellow.entities.units.entity;

import arc.util.io.*;
import mindustry.gen.*;
import yellow.comp.*;
import yellow.io.*;

public class WeaponMasterEntity extends UnitEntity implements ToggleWeaponsc{

    private static final int mappingId = EntityMapping.register("weapon-master-unit", WeaponMasterEntity::new);

    public WeaponMasterEntity(){
        super();
    }

    @Override
    public int classId(){
        return mappingId;
    }

    @Override
    public void read(Reads read){
        super.read(read);

        YellowTypeIO.readToggleWeapons(mounts, read, true);
    }

    @Override
    public void write(Writes write){
        super.write(write);

        YellowTypeIO.writeToggleWeapons(mounts, write);
    }
}
