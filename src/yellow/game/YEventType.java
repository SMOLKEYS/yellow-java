package yellow.game;

import mindustry.gen.*;
import yellow.entities.units.*;

public class YEventType{
    
    public static class DeathInvalidationEvent{
        public final Unit defier;
        
        public DeathInvalidationEvent(Unit defier){
            this.defier = defier;
        }
    }
}
