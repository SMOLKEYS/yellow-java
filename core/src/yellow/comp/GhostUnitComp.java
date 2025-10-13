package yellow.comp;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.type.*;
import yellow.gen.*;
import yellow.type.unit.*;

@EntityComponent
@EntityDef({GhostUnitc.class, Unitc.class, TimedKillc.class})
abstract class GhostUnitComp implements Unitc, TimedKillc{
    @Import float x, y, time, lifetime, hitSize;
    @Import Ability[] abilities;
    @Import UnitType type;

    public float lifetimef(){
        return Mathf.clamp(time / lifetime);
    }

    @Override
    public void add(){
        lifetime = type().lifetime + Mathf.random(type().lifetimeRnd);
    }

    @Override
    @Replace(69)
    public void kill(){
        remove();
    }

    @Override
    @Replace(69)
    public void destroy(){
        remove();
    }

    @Override
    public void draw(){
        Fill.light(x, y, 10, Mathf.lerp(hitSize * 1.9f, hitSize * 0.9f, lifetimef()), Color.white, Color.clear);
    }

    @Override
    public GhostUnitType type(){
        return (GhostUnitType) type;
    }

    @Override
    public void remove(){
        type().despawnEffect.at(x + type().despawnEffectOffset.x, y + type().despawnEffectOffset.y);

        for(Ability ability : abilities){
            ability.death(self());
        }
    }

    @Override
    public void read(Reads read){
        lifetime = read.f();
    }

    @Override
    public void write(Writes write){
        write.f(lifetime);
    }
}
