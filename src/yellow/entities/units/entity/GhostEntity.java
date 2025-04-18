package yellow.entities.units.entity;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;
import yellow.entities.units.*;

public class GhostEntity extends UnitEntity{
    private static final int mappingId = EntityMapping.register("ghost-unit", GhostEntity::new);

    protected boolean inited = false;
    public float lifetime = 0f;

    public GhostEntity(){
        super();
    }

    private void init(){
        if(inited) return;
        inited = true;
        lifetime = type().lifetime + Mathf.random(type().lifetimeRnd);
    }

    public float lifetimef(){
        return Mathf.clamp(lifetime / type.lifetime);
    }

    @Override
    public int classId(){
        return mappingId;
    }

    @Override
    public int cap(){
        return Integer.MAX_VALUE;
    }

    @Override
    public GhostUnitType type(){
        return (GhostUnitType) super.type();
    }

    @Override
    public void kill(){
        type().despawnEffect.at(x + type().despawnEffectOffset.x, y + type().despawnEffectOffset.y);
        super.remove();
    }

    @Override
    public void destroy(){
        type().despawnEffect.at(x + type().despawnEffectOffset.x, y + type().despawnEffectOffset.y);
        super.remove();
    }

    @Override
    public void update(){
        super.update();
        init();

        lifetime -= Time.delta;

        if(lifetime <= 0f){
            super.remove();
            type().despawnEffect.at(x + type().despawnEffectOffset.x, y + type().despawnEffectOffset.y);
        }
    }

    @Override
    public void draw(){
        super.draw();

        Fill.light(x, y, 10, Mathf.lerp(hitSize * 1.9f, hitSize * 0.9f, lifetimef()), Color.white, Color.clear);
    }

    @Override
    public void read(Reads read){
        super.read(read);
        short s = read.s();

        if(s == 0){
            inited = read.bool();
            lifetime = read.f();
        }
    }

    @Override
    public void write(Writes write){
        super.write(write);
        write.s(0);
        write.bool(inited);
        write.f(lifetime);
    }
}
