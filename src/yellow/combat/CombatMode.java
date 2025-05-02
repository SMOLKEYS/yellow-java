package yellow.combat;

import arc.*;
import mindustry.game.*;

public class CombatMode{
    public CombatInput input;

    public void init(){
        input = new CombatInput();

        Events.run(EventType.Trigger.update, this::update);
    }

    public void update(){
        input.updateSwapListener();
    }
}
