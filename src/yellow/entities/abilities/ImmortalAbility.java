package yellow.entities.abilities;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;

import static mindustry.Vars.*;

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