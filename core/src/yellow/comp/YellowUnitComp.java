package yellow.comp;

import arc.*;
import arc.input.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import ent.anno.Annotations.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import yellow.*;
import yellow.content.*;
import yellow.equality.*;
import yellow.gen.*;
import yellow.math.*;
import yellow.util.*;
import yellow.util.Inputs.*;

@EntityComponent
@EntityDef({YellowUnitc.class, Unitc.class, WeaponMasterUnitc.class, SingleInstanceUnitc.class})
abstract class YellowUnitComp implements Unitc, WeaponMasterUnitc, SingleInstanceUnitc{

    @Import float x, y, hitSize, rotation;
    @Import Team team;
    @Import Vec2 vel;

    public float bowlingCooldown;
    public boolean bowlingMode;
    private Vec2 bowlingVec = new Vec2();

    private float queuedKillCountdown = 30f;
    private boolean queuedKillTeleport = false;
    private transient Unit queuedKill = null;

    private transient Seq<Unit> bowled = new Seq<>(128);
    private transient InputSequence discombobulation = Inputs.keySequence(30f, true, KeyCode.x, KeyCode.a, KeyCode.mouseRight);


    @Override
    public void update(){
        updateBowling();
        updateUnitKillers();
        updateXAM2();
    }

    public void updateBowling(){
        if(Core.input.keyTap(KeyCode.controlLeft) && vel.len() >= 1f){
            bowlingMode = !bowlingMode;
            if(bowlingMode && bowlingVec != null) bowlingVec.set(vel);
        }

        if(bowlingMode){
            vel.set(bowlingVec);
            rotation += 10f;
        }

        Units.nearbyEnemies(team, x, y, hitSize, un -> {
            float speed = vel.len();
            if((speed >= 15.5f || bowlingMode) && bowlingCooldown <= 0f){
                float overshoot = speed - (bowlingMode ? 0f : 15.5f);
                vel.inv().scl(2.7f);
                YellowSounds.bowlingStrike.at(self());
                Damage.dynamicExplosion(x, y, 0f, 155f, 0f, (8f * 10f) + overshoot, true, false, team);
                bowled.addUnique(un);

                YellowGameStyles.bowled.spawn(null);

                Units.nearbyEnemies(team, un.x, un.y, un.hitSize + (8f * 8f), un2 -> {
                    un2.vel.add(10f, 10f).setAngle(Angles.angle(x, y, un.x, un.y));
                    Damage.damageUnits(team, un2.x, un2.y, un2.hitSize + (8f * 5f), Mathf.random((un2.health * 1.5f) + (un2.maxHealth / 3f)), un3 -> true,  un3 -> {
                        un3.vel.add(10f, 10f).setAngle(Angles.angle(x, y, un2.x, un2.y));
                        bowled.addUnique(un3);
                    });
                    bowled.addUnique(un2);
                });

                // only track dead ones
                bowled.remove(filt -> !filt.dead());

                int count = bowled.size;
                if(Mathy.inRange(count, 1, 20)){
                    YellowGameStyles.strike.spawn(count);
                }else if(Mathy.inRange(count, 21, 69)){
                    YellowGameStyles.bigStrike.spawn(count);
                }else if(count >= 70){
                    YellowGameStyles.biggestStrike.spawn(count);
                }

                bowled.clear();

                bowlingCooldown = Yellow.debug ? 60f : 60*10f;
                bowlingMode = false;
            }
        });

        bowlingCooldown = Math.max(bowlingCooldown - Time.delta, 0f);
    }

    public void updateUnitKillers(){
        if(queuedKill != null){
            float qkx = queuedKill.x(), qky = queuedKill.y(), qkangle = Angles.angle(qkx, qky, x, y), qkhs = queuedKill.hitSize();

            if(!queuedKillTeleport){
                YellowFx.quickKillTeleport.at(x, y, 0f, queuedKill);
                queuedKillTeleport = true;
            }

            x = qkx + Angles.trnsx(qkangle, 15f + (qkhs * 1.4f));
            y = qky + Angles.trnsy(qkangle, 15f + (qkhs * 1.4f));
            rotation = qkangle + 180;

            queuedKillCountdown -= Time.delta;

            if(queuedKillCountdown <= 0f){
                EqualityDamage.annihilate(queuedKill, false, true, null, null);
                if(queuedKill.dead()){
                    queuedKill = null;
                    queuedKillTeleport = false;
                }
            }

            return;
        }

        Groups.unit.each(fil -> fil != self(), unit -> {
            if(unit.getClass().getName().contains("EmpathyUnit")) queuedKill = unit;
        });

        queuedKillCountdown = 30f;
    }

    public void updateXAM2(){
        if(
                discombobulation.poll()
                // if you know, you know
                || Inputs.keyDown(KeyCode.controllerA, KeyCode.controllerY, KeyCode.controllerRBumper)
        ){
            destroy();
            YellowSounds.bowlingStrike.play(3f);
        }
    }

}
