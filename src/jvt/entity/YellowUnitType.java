package jvt.entites.units;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.ai.types.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class YellowUnitType extends UnitType{
    // very helth
    public float health = Float.MAX_VALUE, armor = FLOAT.MAX_VALUE;
    public TextureRegion region = Core.atlas.find("yellow");
    public boolean flying = true;
    public Prov<? extends Unit> constructor = UnitEntity::create;
};