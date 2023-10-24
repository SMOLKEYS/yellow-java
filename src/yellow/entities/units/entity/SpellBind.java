package yellow.entities.units.entity;

import arc.util.io.*;
import mindustry.gen.*;
import yellow.internal.*;
import yellow.type.*;

import static yellow.internal.util.YellowUtils.*;

public class SpellBind implements Savec{
    public Spell spell;
    public int cooldown;

    public SpellBind(Spell spell){
        this.spell = spell;
    }

    public void update(){
        if(cooldown > 0) cooldown--;
    }

    public void cast(Unit unit){
        if(!ready()) return;
        spell.onCast.get(unit);
        cooldown = spell.cooldown;
    }

    public boolean ready(){
        return cooldown <= 0;
    }

    @Override
    public void write(Writes write){
        internalLog("begin write (" + cooldown + ") for " + spell);
        write.i(cooldown);
        internalLog("write complete");
    }
    @Override
    public void read(Reads read){
        int i = read.i();
        internalLog("begin read (" + i + ") for " + spell);
        cooldown = i;
        internalLog("read complete");
    }
}
