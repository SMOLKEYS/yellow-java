package yellow.entities.units.entity;

import arc.math.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.gen.UnitEntity.*;
import yellow.content.*;
import yellow.entities.units.*;

public class GhostUnitEntity extends UnitEntity{
    public float lifetime;
    
    public float lifetimef(){
        return lifetime / ((GhostUnitType)type).lifetime;
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
}