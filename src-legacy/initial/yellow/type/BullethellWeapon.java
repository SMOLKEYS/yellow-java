package yellow.type;

import arc.func.*;
import arc.math.*;
import arc.struct.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import yellow.ai.*;
import yellow.entities.units.*;
import yellow.entities.units.entity.*;

public class BullethellWeapon extends DisableableWeapon{
    /** Weapon groups defined by the player in the Session Manager. */
    public static final ObjectMap<String, Seq<BullethellWeapon>> groups = ObjectMap.of("all", new Seq<>());

    /** What mode of movement this weapon uses in a bullethell session. */
    public int moveMode;
    /** Difficulty ranking for this weapon. In a bullethell session with a set difficulty value above 0, the AI will use weapons under that difficulty and any weapons below the set value. */
    public int difficulty;
    /** Determines how long this weapon lasts before switching to a new weapon. */
    public JavaFloatRange time = new JavaFloatRange(200, 300);
    /** Miscellaneous user-defined code used for the bullethell AI. Triggered when the weapon shoots during a bullethell session. */
    public Cons<BullethellAI> onShoot = b -> {};

    public BullethellWeapon(String name){
        super(name);

        mountType = BullethellWeaponMount::new;
    }

    @Override
    protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation){
        super.shoot(unit, mount, shootX, shootY, rotation);
        YellowUnitEntity ent = (YellowUnitEntity) unit;
        if(ent.controller() instanceof BullethellAI) onShoot.get((BullethellAI) ent.controller());
    }

    public static class JavaFloatRange{
        public float start, end;

        public JavaFloatRange(float start, float end){
            this.start = start;
            this.end = end;
        }

        public float random(){
            return Mathf.random(start, end);
        }
    }
}
