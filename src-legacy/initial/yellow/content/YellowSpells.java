package yellow.content;

import arc.input.*;
import mindustry.content.*;
import mindustry.gen.*;

public class YellowSpells{

    public static Spell fireCircle, dash;

    public static void load(){
        fireCircle = new Spell("fire-circle"){{
           cooldown = 180;
           castEffect = YellowFx.fireCircleEffect;
           casts.add(new StatusEffectInfliction(){{
               range = 8*20;
               effect = StatusEffects.burning;
               minTime = 5*60;
               maxTime = 15*60;
           }});
        }};

        dash = new Spell("dash"){{
            cooldown = 30;

            //TODO probably bad
            castListener = new KeyTapListener<Unit>(KeyCode.period, 1, 10, e -> {
                var u = YellowUtils.getActiveYellow(e.team);

                if(u != null){
                    for(SpellBind b: u.spells()){
                        if(b.spell == this && b.ready()) b.cast(u);
                    }
                }
            });

            casts.add(new CodeCast(unit -> {
                unit.vel().trns(unit.rotation(), 10);
                YState.INSTANCE.setDashes(YState.INSTANCE.getDashes() + 1);
            }));
        }};
    }
}
