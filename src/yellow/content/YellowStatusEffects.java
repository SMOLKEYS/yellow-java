package yellow.content;

import arc.math.Mathf;
import arc.graphics.Color;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import yellow.content.YellowFx;

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
                if(Mathf.chanceDelta(0.6)){
                    unit.damage(1);
                    YellowFx.textIndicator.at(unit.x, unit.y, 0f, Color.black, "-1");
                }
            }
        };
    }
}
