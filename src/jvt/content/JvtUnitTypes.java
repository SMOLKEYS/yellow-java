package jvt.content;

//i have no idea what im doing lmfao
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.gen.*;
import mindustry.ai.types.DefenderAI;

public class JvtUnitTypes implements ContentList{
    public static UnitType
    
    //yellow units
    yellowAir;
    
    @Override
    public void load(){
        yellow = new UnitType("yellow"){{
            AIControlller = DefenderAI;
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