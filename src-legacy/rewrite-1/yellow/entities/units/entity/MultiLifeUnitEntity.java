package yellow.entities.units.entity;

import arc.*;
import arc.func.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;

public class MultiLifeUnitEntity extends WeaponSpecialistEntity{
    private static final int mappingId = EntityMapping.register("multilife-unit", MultiLifeUnitEntity::new);

    protected boolean inited = false;
    public int lives = 0;
    public float invFrames = 0f;

    public MultiLifeUnitEntity(){
        super();
    }

    private void init(){
        if(inited) return;
        inited = true;
    }

    public int lives(){
        return lives;
    }

    public void lives(int lives){
        this.lives = lives;
    }

    public float livesf(){
        return (float) lives / type().lives;
    }

    public boolean hasLives(){
        return lives > 0;
    }

    public void removeLife(){
        health = type().health;
        dead = false;
        elevation = 1;
        lives--;
        invFrames = type().invFrames;

        if(type().deathStopEffect != null) type().deathStopEffect.at(x, y);

        Cons<MultiLifeUnitEntity> m = type().perDeath.get(lives);
        if(m != null) m.get(this);

        for(var a: abilities){
            if(a instanceof DeathStopAbility d) d.onDeath(this);
        }

        Events.fire(new DeathStopEvent(this));
    }

    @Override
    public void rawDamage(float amount){
        if(invFrames <= 0) super.rawDamage(amount);
    }

    @Override
    public int classId(){
        return mappingId;
    }

    @Override
    public void update(){
        super.update();
        init();

        if(invFrames > 0) invFrames -= Time.delta;
    }

    @Override
    public MultiLifeUnitType type(){
        return (MultiLifeUnitType) super.type();
    }

    @Override
    public void destroy(){
        if(lives > 0){
            removeLife();
            return;
        }

        super.destroy();
    }

    @Override
    public void kill(){
        if(lives > 0){
            removeLife();
            return;
        }

        super.kill();
    }

    @Override
    public void read(Reads read){
        super.read(read);

        inited = read.bool();
        lives = read.i();
        Log.info("Data collected from world: @, @", inited, lives);
    }

    @Override
    public void write(Writes write){
        super.write(write);

        write.bool(inited);
        write.i(lives);
        Log.info("Data passed to world: @, @", inited, lives);
    }
}
