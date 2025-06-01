package yellow.entities.units.entity;

import arc.math.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.io.*;
import yellow.ai.*;
import yellow.comp.*;
import yellow.entities.units.*;

public class YellowUnitEntity extends SingleInstanceEntity implements Defendc{

    private static final int mappingId = EntityMapping.register("yellow-unit", YellowUnitEntity::new);

    public Seq<Unit> defenders;

    public YellowUnitEntity(){
        super();
        defenders = new Seq<>();
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
    public YellowUnitType type(){
        return (YellowUnitType) super.type();
    }

    @Override
    public void update(){
        super.update();
        updateDefenders();
    }

    @Override
    public Seq<Unit> defenders(){
        return defenders;
    }

    @Override
    public void updateDefenders(){
        defenders.remove(e -> e.dead || !e.isValid() || !(e.controller() instanceof ShielderAI));
        Unit f = Groups.unit.find(e -> e.controller() instanceof ShielderAI a && a.owner == this && !defenders.contains(e));
        if(f != null) defenders.add(f);

        float sides = 360f / defenders.size;

        for(int i = 0; i < defenders.size; i++){
            ShielderAI ai = (ShielderAI) defenders.get(i).controller();

            ai.pos.set(
                    x + Angles.trnsx(sides * i, 50, 50),
                    y + Angles.trnsy(sides * i, 50, 50)
            );
        }
    }

    @Override
    public void read(Reads read){
        super.read(read);

        for(int i = 0; i < read.i(); i++){
            defenders.add(TypeIO.readUnit(read));
        }
    }

    @Override
    public void write(Writes write){
        super.write(write);

        write.i(defenders.size);
        defenders.each(e -> TypeIO.writeUnit(write, e));
    }
}
