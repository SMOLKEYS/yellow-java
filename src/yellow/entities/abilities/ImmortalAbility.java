package yellow.entities.abilities;

import mindustry.entities.abilities.*;
import mindustry.gen.*;

public class ImmortalAbility extends Ability{
    public float respawnTime = 0f;
    
    public ImmortalAbility(float respawnTime){
        this.respawnTime = respawnTime;
    }
    
    public ImmortalAbility(){}
    
    @Override
    public void death(Unit unit){
        unit.type.spawn(unit.team, unit.x, unit.y);
    }
}