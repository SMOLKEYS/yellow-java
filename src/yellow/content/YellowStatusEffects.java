package yellow.content;

import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;

public class YellowStatusEffects{
    public static StatusEffect
    
    demise;

    public static void load(){
        
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
