package yellow.ai;

import arc.math.geom.*;
import arc.struct.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import yellow.entities.units.*;
import yellow.entities.units.entity.*;
import yellow.type.*;

import java.util.*;

public class BullethellAI extends AIController{

    private final UnitController originalAI;

    public int gamemode;

    // 0 - still
    // 1 - approach
    // 2 - follow
    // 3 - rigid follow
    public int moveMode = 0;
    public Unit target;
    public int remainingWeaponTime, remainingSessionTime;
    public int difficulty;
    public boolean allowWeaponRepeating;
    public Vec2 offset = new Vec2();
    public Seq<BullethellWeapon> weapons;
    public BullethellWeapon activeWeapon;

    public BullethellAI(Unit target, UnitController originalAI, int difficulty, int gamemode, Seq<BullethellWeapon> weapons){
        super();

        this.target = target;
        this.originalAI = originalAI;
        this.difficulty = difficulty;
        this.gamemode = gamemode;
        this.weapons = weapons.select(e -> e.difficulty == this.difficulty);

        this.activeWeapon = this.weapons.random();
        this.remainingWeaponTime = this.activeWeapon.time.random();
        this.moveMode = this.activeWeapon.moveMode;

        this.remainingSessionTime = this.activeWeapon.time.end;
    }

    public void initWeapons(){
        for(WeaponMount m : unit().mounts()){
            if(m instanceof BullethellWeaponMount){
                BullethellWeaponMount b = (BullethellWeaponMount) m;
                b.enabled = b.weapon == activeWeapon;
            }
        }
    }

    @Override
    public void updateUnit(){
        super.updateUnit();

        if(remainingSessionTime <= 0) unit.controller(originalAI);

        remainingWeaponTime--;
        remainingSessionTime--;

        if(remainingWeaponTime <= 0){

            BullethellWeapon nextWeapon;

            if(allowWeaponRepeating){
                nextWeapon = weapons.random(activeWeapon);
            }else{
                nextWeapon = weapons.random();
            }

            activeWeapon = nextWeapon;
            remainingWeaponTime = nextWeapon.time.random();
            moveMode = nextWeapon.moveMode;
        }
    }

    @Override
    public void updateWeapons(){

    }

    @Override
    public void updateMovement(){
        switch(moveMode){
            case 0:
                BullethellWeaponMount weapon = (BullethellWeaponMount) Objects.requireNonNull(((YellowUnitEntity) unit).findMount(activeWeapon));

                weapon.target = target;
                weapon.shoot = true;
                break;
        }
    }
}
