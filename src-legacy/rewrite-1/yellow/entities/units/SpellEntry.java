package yellow.entities.units;

import arc.util.*;
import arc.util.io.*;
import yellow.comp.*;
import yellow.type.*;

public class SpellEntry implements Savec{
    public Spell spell;
    public float cooldown;

    public SpellEntry(Spell spell){
        this.spell = spell;
    }

    public boolean ready(Magicc user){
        return cooldown <= 0 && user.mana() >= spell.manaCost;
    }

    public void use(Magicc user){
        spell.use(user, this);
    }

    public void update(){
        cooldown -= Time.delta;
    }

    @Override
    public void read(Reads read){
        cooldown = read.f();
    }

    @Override
    public void write(Writes write){
        write.f(cooldown);
    }
}
