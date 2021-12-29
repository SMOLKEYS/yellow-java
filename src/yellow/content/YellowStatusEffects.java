package yellow.content;

import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.gen.*;

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