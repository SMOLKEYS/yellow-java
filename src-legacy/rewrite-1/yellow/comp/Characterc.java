package yellow.comp;

import mindustry.gen.*;
import yellow.world.meta.*;

public interface Characterc<T extends UnitEntity>{
    
    T unitWorld();
    
    default GameCharacter character(){
        return GameCharacter.empty;
    }
    
    default boolean inWorld(){
        return unitWorld() != null;
    }
    
}