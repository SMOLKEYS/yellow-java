package yellow.type;

import arc.struct.*;
import kotlin.ranges.*;
import yellow.entities.units.*;

/** Special weapon type used in {@link yellow.ai.BullethellAI}. */
public class BullethellPattern extends DisableableWeapon{
    public static final ObjectMap<String, Seq<BullethellPattern>> groups = new ObjectMap<>();
    public static final Seq<BullethellPattern> all = new Seq<>();

    /** The intensity of this pattern. Used when selecting a difficulty in a bullethell session. */
    public int intensity = 1;
    public IntRange timespan = new IntRange(60*15, 60*25);

    public BullethellPattern(String name, String group){
        super(name);
        mountType = BullethellWeaponMount::new;
        all.add(this);
        if(groups.containsKey(group)){
            groups.get(group).add(this);
        }else{
            groups.put(group, new Seq<>()).add(this);
        }
    }

}
