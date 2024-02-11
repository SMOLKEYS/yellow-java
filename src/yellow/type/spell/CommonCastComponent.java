package yellow.type.spell;

import mindustry.gen.*;

public class CommonCastComponent{

    /** The reach of this cast, in world units. */
    public float range;
    public int flags = InflictionFlags.enemies;

    public void apply(Unit caster){

    }


    public static class InflictionFlags{
        public static int

                enemies = 1,
                allies = 1 << 1,
                any = enemies | allies;
    }
}
