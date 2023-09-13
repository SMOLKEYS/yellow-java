package yellow.entities.units.entity;

import arc.util.io.*;
import mindustry.gen.*;
import yellow.internal.*;
import yellow.type.*;

import static yellow.internal.util.YellowUtils.*;

public class SpellBind implements Savec {
    public Spell spell;
    public float cooldown;

    public SpellBind(Spell spell){
        this.spell = spell;
    }

    public void update(){
        if(cooldown >= 0) cooldown--;
    }

    public void cast(Unit unit){
        if(!ready()) return;
        spell.onCast.get(unit);
        cooldown = spell.cooldown;
    }

    public boolean ready(){
        return cooldown >= 0f;
    }

    @Override
    public void write(Writes write){
        internalLog("begin write for " + spell);
        write.f(cooldown);
        internalLog("write complete");
    }

    @Override
    public void read(Reads read){
        internalLog("begin read for " + spell);
        cooldown = read.f();
        internalLog("read complete");
    }
}
