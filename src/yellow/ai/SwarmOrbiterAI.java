package yellow.ai;

import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.blocks.storage.*;
import yellow.math.*;

/** An AI that orbits a target unit. If no target is found, defaults to the team core. If that is also not found (somehow), does nothing. */
public class SwarmOrbiterAI extends AIController{
    public static float dstCap = 8*65f;

    /** The target unit type to scan for. */
    public Boolf<Unit> targetUnit = p -> true;

    /** The current unit to orbit. */
    protected Unit follow;
    /** The target core building to orbit. */
    protected CoreBlock.CoreBuild core;
    protected float dst = 80f, maxDst = 680f;
    /** If specified, the AI will orbit in reverse. */
    protected boolean useOpposite;
    protected float peakSpeedRD = 6f;


    public SwarmOrbiterAI(){
        super();
    }

    public SwarmOrbiterAI(Boolf<Unit> targetUnit){
        super();
        this.targetUnit = targetUnit;
    }

    @Override
    public void init(){
        //hurricane
        dst = dst + Mathf.random(maxDst);
        if(!useOpposite) useOpposite = Mathf.chance(0.5);
    }

    @Override
    public void updateMovement(){

        if(follow == null){
            follow = Groups.unit.find(e -> e.team == unit.team && targetUnit.get(e));
        }else if(follow.team != unit.team || follow.dead() || !follow.isValid()){
            follow = null;
        }

        if(core == null){
            core = unit.team.data().core();
        }

        //prefer unit
        Position target = follow != null ? follow : core;

        float dMul = target != null ? Mathy.lerpc(1f, peakSpeedRD, Mathy.dstPos(unit, target) - dst, dstCap) : 0f;
        float fSpeed = unit.speed() * (dMul);

        if(target != null){
            if(useOpposite){
                circleOpposite(target, dst, fSpeed);
            }else{
                circle(target, dst, fSpeed);
            }
        }

        faceMovement();
    }

    public void circleOpposite(Position target, float circleLength, float speed){
        if(target == null) return;

        vec.set(target).sub(unit);

        if(vec.len() < circleLength){
            vec.rotate(-(circleLength - vec.len()) / circleLength * 180f);
        }

        vec.setLength(speed);

        unit.moveAt(vec);
    }
}
