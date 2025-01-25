package yellow.ai;

import mindustry.ai.*;
import mindustry.ai.types.*;

public class YellowUnitCommand{

    public static final UnitCommand

    wander = new UnitCommand("wander", "right", u -> {
        var ai = new WanderingAI();
        ai.unit(u);
        return ai;
    }),

    fly = new UnitCommand("fly", "unit", u -> {
    	var ai = new FlyingAI();
    	ai.unit(u);
    	return ai;
    });
}
