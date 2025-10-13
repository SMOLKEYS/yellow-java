package yellow.type;

import arc.*;
import arc.func.*;
import arc.input.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;

public class Spell{

    public String name, displayName, description;
    /** Required mana to cast this spell. */
    public float manaCost = 1f;
    /** Cooldown time when casting this spell, in ticks. */
    public float cooldown = 60f;
    /** Type of spell entry to be used. */
    public Func<Spell, SpellEntry> spellType = SpellEntry::new;
    /** Effect spawned upon casting this spell. */
    public Effect castEffect = Fx.none;
    /** Various other stats this spell has. */
    public Stats stats = new Stats();
    /** Persistent keybind associated with this spell. */
    public final KeyBinds.KeyBind keyBind;
    public final SpellBinding binder;

    public Spell(String name){
        this.name = name;
        this.displayName = Core.bundle.get("spell." + name + ".name");
        this.description = Core.bundle.get("spell." + name + ".description");

        this.keyBind = new KeyBinds.KeyBind(){
            @Override
            public String name(){
                return "yellow-spell";
            }

            @Override
            public KeyBinds.KeybindValue defaultValue(InputDevice.DeviceType type){
                return KeyCode.unset;
            }
        };

        binder = new SpellBinding(keyBind.defaultValue(InputDevice.DeviceType.keyboard), this);

        YellowContent.spells.add(this);
    }

    public static void loadListener(){
        Events.run(EventType.Trigger.update, () -> {
            if(Vars.player.unit() instanceof Magicc m){
                for(SpellEntry s : m.spells()){
                    if(s.spell.getKeyBind() instanceof KeyCode k && Core.input.keyTap(k)) s.use(m);
                }
            }
        });
    }

    public KeyBinds.KeybindValue getKeyBind(){
        return binder.defaultValue(InputDevice.DeviceType.keyboard);
    }

    public void addStats(){
        stats.add(YellowStats.cooldown, cooldown/60, StatUnit.seconds);
        stats.add(YellowStats.manaCost, manaCost, YellowStatUnits.manaPoints);
    }

    public void handleStats(Table t){
        addStats();

        t.add("[lightgray]" + YellowStats.name.localized() + ": [accent]" + displayName + "[]").row();

        //a minute amount of code grabby
        //ContentInfoDialog line 66-89
        for(StatCat cat : stats.toMap().keys()){
            OrderedMap<Stat, Seq<StatValue>> map = stats.toMap().get(cat);

            if(map.size == 0) continue;

            if(stats.useCategories){
                t.add("@category." + cat.name).color(Pal.accent).fillX();
                t.row();
            }

            for(Stat stat : map.keys()){
                t.table(inset -> {
                    inset.left();
                    inset.add("[lightgray]" + stat.localized() + ":[] ").left().top();
                    Seq<StatValue> arr = map.get(stat);
                    for(StatValue value : arr){
                        value.display(inset);
                        inset.add().size(10f);
                    }

                }).fillX().padLeft(1);
                t.row();
            }
        }
    }

    public void use(Magicc user, SpellEntry spell){
        useConsume(user, spell);
        spawnEffects(user, spell);
    }

    public void useConsume(Magicc user, SpellEntry spell){
        spell.cooldown = cooldown;
        user.consume(manaCost);
    }

    public void spawnEffects(Magicc user, SpellEntry spell){
        castEffect.at(user);
    }
}
