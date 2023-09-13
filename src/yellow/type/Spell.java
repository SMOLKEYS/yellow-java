package yellow.type;

import arc.*;
import arc.func.*;
import mindustry.gen.*;
import yellow.entities.units.entity.*;
import yellow.internal.*;

public class Spell implements Namec {
    public String name, displayName = nameLocalized();
    public float x, y, cooldown;
    public Cons<Unit> onCast = unit -> {};
    public Func<Spell, SpellBind> spellType = SpellBind::new;

    public Spell(String name){
        this.name = name;
        this.x = this.y = this.cooldown = 0f;
    }

    @Override
    public String nameLocalized(){
        displayName = Core.bundle.get("spell." + name + ".name");
        return displayName;
    }

    @Override
    public String descriptionLocalized(){
        return Core.bundle.get("spell." + name + ".description");
    }

    @Override
    public String toString(){
        return name == null || name.isEmpty() ? "Spell" : "Spell: " + name;
    }
}
