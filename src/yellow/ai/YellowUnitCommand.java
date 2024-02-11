package yellow.ai;

import mindustry.ai.*;

public class YellowUnitCommand{

    public static final UnitCommand

    wander = new UnitCommand("wander", "right", u -> {
        WanderingAI ai = new WanderingAI();
        ai.unit(u);
        return ai;
    });
}
