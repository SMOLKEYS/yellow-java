package yellow.content;

import arc.*;
import arc.struct.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.Color.*;
import arc.math.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.game.Team.*;
import mindustry.game.EventType.*;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class YellowStatusEffects implements ContentList{
    public static StatusEffect
    
    demise;
    
    @Override
    public void load(){
        
        demise = new StatusEffect("demise"){{
            color = Team.derelict.color;
            damage = Float.MAX_VALUE;
            show = true;
        }
            
            @Override
            public void update(Unit unit, float time){
                super.update(unit, time);
                
                unit.kill();
            }
        };
    }
}