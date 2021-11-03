package jvt.content;

//a
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
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class JvtUnitTypes implements ContentList{
    public static UnitType
    
    //yellow units
    yellowAir;
    
    @Override
    public void load(){
        yellow = new UnitType("yellow"){{
            flying = true;
            health = 2147483647f;
            armor = 2147483647f;
            speed = 3;
            accel = 0.08;
            drag = 0.01;
            range = 240;
            maxRange = 240;
        }};
    }
}