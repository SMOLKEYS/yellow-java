package yellow.content;

import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import yellow.ctype.FallbackContentList;

public class YellowStatusEffects implements FallbackContentList{
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
