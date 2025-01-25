package yellow.entities.effect;

import arc.graphics.*;
import arc.util.*;
import mindustry.entities.*;

/** A variant of {@link Effect} that spawns a random effect instead of a fixed one. */
public class RandomEffect extends Effect{
    public Effect[] effects;

    public RandomEffect(Effect... effects){
        this.effects = effects;
    }

    @Override
    public void create(float x, float y, float rotation, Color color, Object data){
        if(shouldCreate()) Structs.random(effects).create(x, y, rotation, color, data);
    }
}
