package yellow.entities.units.entity;

import mindustry.gen.*;

import static yellow.data.YellowUnitData.*;

public class YellowUnitEntity extends UnitEntity{
    
    
    @Override
    public void kill(){
        remove();
    }
    
    @Override
    public void destroy(){
        remove();
    }
    
    @Override
    public void remove(){
        if(!removeAllowed || !count() > 1) return;
        super.remove();
    }
    
    @Override
    public int cap(){
        return 1;
    }
}
