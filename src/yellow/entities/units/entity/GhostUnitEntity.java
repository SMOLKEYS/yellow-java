package yellow.entities.units.entity;

import arc.math.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.gen.UnitEntity.*;
import yellow.content.*;
import yellow.entities.units.*;

    /**
     * Semi-steal from Progressed Materials.
     */
public class GhostUnitEntity extends UnitEntity{
    public float lifetime;
    
    public float lifetimef(){
        return lifetime / ((GhostUnitType)type).lifetime;
    }
    
    public void clampLifetime(){
        lifetime = Mathf.clamp(lifetime, 0f, ((GhostUnitType)type).lifetime);
    }
    
    @Override
    public int cap(){
        return count() + 1;
    }
    
    @Override
    public void write(Writes write){
        super.write(write);
        write.f(lifetime);
    }
    
    @Override
    public void read(Reads read){
        super.read(read);
        lifetime = read.f();
    }
    
    @Override
    public int classId(){
        return YellowUnitTypes.classID(GhostUnitEntity.class);
    }
}
