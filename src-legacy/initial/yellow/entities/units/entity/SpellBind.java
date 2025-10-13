package yellow.entities.units.entity;

import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;

public class SpellBind implements Savec{
    public Spell spell;
    public float cooldown;

    public SpellBind(Spell spell){
        this.spell = spell;
    }

    public void update(){
        if(cooldown > 0) cooldown -= Time.delta;
    }

    public void cast(Unit unit){
        if(!ready()) return;
        spell.castEffect.at(unit);
        spell.casts.each(e -> {
            e.apply(unit);
        });
        cooldown = spell.cooldown;
    }

    public boolean ready(){
        return cooldown <= 0;
    }

    @Override
    public void write(Writes write){
        internalLog("begin write (" + cooldown + ") for " + spell);
        write.f(cooldown);
        internalLog("write complete");
    }
    @Override
    public void read(Reads read){
        float f = read.f();
        internalLog("begin read (" + f + ") for " + spell);
        cooldown = f;
        internalLog("read complete");
    }
}
