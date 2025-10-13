package yellow.comp;

import arc.math.geom.*;

@SuppressWarnings("unused")
public interface Magicc extends Position{

    float manaf();

    float mana();

    void mana(float mana);

    void consume(float mana);

    SpellEntry[] spells();

    void spells(SpellEntry[] spells);

    void useSpell(SpellEntry spell);
}
