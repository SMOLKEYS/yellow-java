package yellow.input;

import arc.*;
import arc.input.*;
import arc.struct.*;
import yellow.type.*;

public class SpellBinding implements KeyBinds.KeyBind{

    public static final Seq<SpellBinding> inited = new Seq<>(SpellBinding.class);

    private final KeyBinds.KeybindValue def;
    private final String category;
    private final Spell spell;

    public SpellBinding(KeyBinds.KeybindValue def, Spell spell){
        this(def, spell, "yellow-spells");
    }

    public SpellBinding(KeyBinds.KeybindValue def, Spell spell, String category){
        this.def = def;
        this.spell = spell;
        this.category = category;

        inited.add(this);
    }

    @Override
    public String name(){
        return spell.name;
    }

    @Override
    public KeyBinds.KeybindValue defaultValue(InputDevice.DeviceType type){
        return def;
    }

    @Override
    public String category(){
        return category;
    }

    @Override
    public String toString(){
        return "yellow_spell_" + spell.name;
    }
}
