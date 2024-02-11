package yellow.type;

import arc.*;
import arc.func.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.entities.*;
import yellow.entities.units.entity.*;
import yellow.input.*;
import yellow.internal.*;
import yellow.type.spell.*;

@SuppressWarnings("rawtypes")
public class Spell implements Namec, YContent {
    public String name, displayName, description;
    public float x, y;
    public float cooldown;
    public Effect castEffect = Fx.none;
    public Seq<CommonCastComponent> casts = new Seq<>();
    public Func<Spell, SpellBind> spellType = SpellBind::new;
    public CommonKeyListener castListener;

    public Spell(String name) {
        this.name = name;
        this.x = this.y = this.cooldown = 0;

        this.displayName = nameLocalized();
        this.description = descriptionLocalized();

        YellowContentLoader.spells.add(this);
        YellowContentLoader.all.add(this);
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
