package yellow.game;

import mindustry.gen.*;
import yellow.entities.units.*;

public class YEventType{
    
    /** Fired when a unit defies death. */
    public static class DeathInvalidationEvent{
        public final Unit defier;
        
        public DeathInvalidationEvent(Unit defier){
            this.defier = defier;
        }
    }
}
