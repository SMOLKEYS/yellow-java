package yellow.world;

import arc.struct.*;
import arc.util.*;
import mindustry.gen.*;
import yellow.ai.*;
import yellow.entities.units.entity.*;
import yellow.type.*;

/** Bullethell session manager. Manages active BullethellAI instances and units under it. */
public class BullethellSessionManager{

    public static YellowUnitEntity activeUnit;
    public static BullethellAI activeAI;

    public static BullethellAI startSession(YellowUnitEntity unit, Unit target, int difficulty, int moveMode, Seq<BullethellWeapon> weapons){
        BullethellAI ai = new BullethellAI(target, unit.controller(), difficulty, moveMode, weapons);
        ai.unit(unit);
        ai.handleWeapons();
        unit.controller(ai);
        activeAI = ai;
        activeUnit = unit;
        return ai;
    }

    public static void endSession(){
        if(activeUnit == null || activeAI == null) return;
        activeUnit.controller(activeAI.originalAI);
        activeAI = null;
        activeUnit = null;
    }

    public static void update(){
        if(activeUnit == null || activeAI == null) return;
        if(activeAI.remainingSessionTime <= 0 || activeUnit.dead()) endSession();

        activeAI.remainingWeaponTime -= Time.delta;
        activeAI.remainingSessionTime -= Time.delta;

        if(activeAI.remainingWeaponTime <= 0){

            BullethellWeapon nextWeapon;

            if(activeAI.allowWeaponRepeating){
                nextWeapon = activeAI.weapons.random(activeAI.activeWeapon);
            }else{
                nextWeapon = activeAI.weapons.random();
            }

            activeAI.activeWeapon = nextWeapon;
            activeAI.remainingWeaponTime = nextWeapon.time.random();
            activeAI.moveMode = nextWeapon.moveMode;
        }
    }
}
