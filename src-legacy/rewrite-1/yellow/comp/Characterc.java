package yellow.comp;

import mindustry.gen.*;

public interface Characterc<T extends UnitEntity>{
    
    T unitWorld();
    
    default GameCharacter character(){
        return GameCharacter.empty;
    }
    
    default boolean inWorld(){
        return unitWorld() != null;
    }
    
}