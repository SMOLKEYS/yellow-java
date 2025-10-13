package yellow.ai;

import arc.math.geom.*;
import arc.struct.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

import java.util.*;

public class BullethellAI extends AIController{

    public final UnitController originalAI;

    //active move mode; see cases below
    public int moveMode;
    //remaining time before a weapon change and overall remaining session time
    public float remainingWeaponTime, remainingSessionTime;
    //difficulty of current session; weapons with difficulty values equal to or below will be used
    public int difficulty;
    //determines if a weapon can have more than one go in a row
    public boolean allowWeaponRepeating;
    //weapons to be used for this session
    public Seq<BullethellWeapon> weapons;
    //currently active weapon to be fired at the target
    public BullethellWeapon activeWeapon;

    //specifics for move modes

    //case 1 - orbiting

    public float orbitRange = 8*25f;

    //case 2 - shifting

    //offset distance from target (at least 14-27 blocks away by default)
    //randomized after some time (usually 1-2 seconds after a weapon shoots)
    public Vec2 offset = new Vec2();

    public float remainingShiftTime;

    public BullethellAI(Unit target, UnitController originalAI, int difficulty, int moveMode, Seq<BullethellWeapon> weapons){
        super();

        this.target = target;
        this.originalAI = originalAI;
        this.difficulty = difficulty;
        this.moveMode = moveMode;
        this.weapons = weapons.select(e -> e.difficulty <= this.difficulty);

        this.activeWeapon = this.weapons.random();
        this.remainingWeaponTime = this.activeWeapon.time.random();
        this.moveMode = this.activeWeapon.moveMode;

        this.remainingSessionTime = this.activeWeapon.time.end;
        this.remainingShiftTime = this.activeWeapon.reload + 120f;
    }

    public void handleWeapons(){
        for(WeaponMount m : unit().mounts()){
            if(m instanceof BullethellWeaponMount){
                BullethellWeaponMount b = (BullethellWeaponMount) m;
                b.enabled = b.weapon == activeWeapon;
            }
        }
    }

    private void genericShoot(){
        BullethellWeaponMount weapon = Objects.requireNonNull(mount(activeWeapon));

        weapon.target = target;
        weapon.shoot = true;
    }

    private BullethellWeaponMount mount(BullethellWeapon weapon){
        for(WeaponMount w: Objects.requireNonNull(unit).mounts){
            if(w instanceof BullethellWeaponMount && w.weapon == weapon) return (BullethellWeaponMount) w;
        }
        return null;
    }

    private void updateOffsetting(){
        //how
    }

    @Override
    public void updateMovement(){
        switch(moveMode){
            case 0: //no movement
                faceTarget();
                genericShoot();
                break;
            case 1: //orbit
                circle(target, orbitRange);
                faceTarget();
                genericShoot();
                break;
            case 2: //movement mimic; TODO

                break;
        }
    }
}
