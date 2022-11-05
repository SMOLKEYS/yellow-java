package yellow.content;

import arc.math.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;

public class YellowStatusEffects{
    public static StatusEffect
    
    demise,
    
    //omg sandz undtertale?!???//
    karma;

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
        
        karma = new StatusEffect("karma"){{
            color = Team.malis.color;
            show = false;
        }
            
            @Override
            public void update(Unit unit, float time){
                if(unit.health <= unit.type.health / 8f) unit.unapply(this);
                if(Mathf.chanceDelta(0.6)){
                    unit.damage(1f);
                }
            }
        };
    }
}
