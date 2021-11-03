package jvt.content;

import mindustry.content.*;
import mindustry.gen.*;
import mindustry.ai.types.DefenderAI;

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
        }}
    }
}