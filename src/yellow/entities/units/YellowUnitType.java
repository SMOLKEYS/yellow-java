package yellow.entities.units;

import yellow.entities.units.entity.*;

public class YellowUnitType extends SingleInstanceUnitType{

    public YellowUnitType(String name){
        super(name);
        constructor = YellowUnitEntity::new;
    }
}
