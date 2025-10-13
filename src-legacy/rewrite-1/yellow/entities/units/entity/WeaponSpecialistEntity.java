package yellow.entities.units.entity;

import arc.*;
import arc.util.io.*;
import mindustry.gen.*;

public class WeaponSpecialistEntity extends CharacterEntity{

    private static final int mappingId = EntityMapping.register("weapon-specialist-unit", WeaponSpecialistEntity::new);

    public WeaponSpecialistEntity(){
        super();
    }

    @Override
    public int classId(){
        return mappingId;
    }

    @Override
    public void read(Reads read){
        super.read(read);

        YellowTypeIO.readToggleWeapons(mounts, read, Core.settings.getBool("yellow-toggle-read-method", true));
    }

    @Override
    public void write(Writes write){
        super.write(write);

        YellowTypeIO.writeToggleWeapons(mounts, write);
    }
}
