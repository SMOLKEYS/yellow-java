package yellow.game;

import mindustry.gen.*;

public class YEventType{
    
    /** Fired when a unit defies death. */
    public static class DeathInvalidationEvent{
        public final Unit unit;
        
        public DeathInvalidationEvent(Unit unit){
            this.unit = unit;
        }
    }
}
