package yellow.entities.units.entity;

import arc.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;

public class MagicSpecialistEntity extends MultiLifeUnitEntity implements Magicc{

    private static final int mappingId = EntityMapping.register("magic-specialist-unit", MagicSpecialistEntity::new);

    public SpellEntry[] spells;
    public float mana;

    public MagicSpecialistEntity(){
        super();
    }

    @Override
    public MagicSpecialistUnitType type(){
        return (MagicSpecialistUnitType) super.type();
    }

    @Override
    public int classId(){
        return mappingId;
    }

    @Override
    public void update(){
        super.update();

        if(mana < type().mana) mana += type().manaRecovery;

        for(SpellEntry e: spells) e.update();
    }

    @Override
    public float manaf(){
        return mana / type().mana;
    }

    @Override
    public float mana(){
        return mana;
    }

    @Override
    public void mana(float mana){
        this.mana = mana;
    }

    @Override
    public void consume(float mana){
        this.mana -= mana;
    }

    @Override
    public SpellEntry[] spells(){
        return spells;
    }

    @Override
    public void spells(SpellEntry[] spells){
        this.spells = spells;
    }

    @Override
    public void useSpell(SpellEntry spell){
        if(spell.ready(this) && Structs.contains(spells, spell)){
            spell.spell.use(this, spell);
        }
    }

    @Override
    public void read(Reads read){
        super.read(read);

         mana = read.f();
         spells = new SpellEntry[type().spells.size];
         for(int i = 0; i < type().spells.size; i++){
             spells[i] = type().spells.get(i).spellType.get(type().spells.get(i)); 
         }

        YellowTypeIO.readSpells(spells, read, Core.settings.getBool("yellow-spell-read-method", true));
    }

    @Override
    public void write(Writes write){
        super.write(write);

        write.f(mana);
        YellowTypeIO.writeSpells(spells, write);
    }
}
