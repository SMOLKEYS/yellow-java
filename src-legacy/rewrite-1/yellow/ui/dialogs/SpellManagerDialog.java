package yellow.ui.dialogs;

import arc.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;

public class SpellManagerDialog extends BaseDialog{

    private SpellEntry[] cache;

    public SpellManagerDialog(){
        super("@yellow.spell-manager");
        addCloseButton();
    }

    public Dialog show(Magicc user, SpellEntry[] spells){
        if(cache == spells) return super.show();

        if(spells.length == 0){
            cache = null;
            cont.clear();
            cont.add("@yellow.spell-manager.no-spells");
            return super.show();
        }

        cont.clear();
        cache = spells;

        cont.add(new Bar(
                () -> Core.bundle.get("yellow.spell-manager.mana"),
                () -> Pal.lancerLaser,
                user::manaf
        )).growX().height(40f).row();

        cont.table(mainManager -> {
            for(SpellEntry s: spells){
                mainManager.button(s.spell.displayName, () -> {
                    user.useSpell(s);
                }).growX().uniformX().touchable(() -> s.ready(user) ? Touchable.enabled : Touchable.disabled);
                mainManager.add(new Bar(
                        () -> s.spell.displayName,
                        () -> s.ready(user) ? Pal.accent : Pal.remove,
                        () -> -(s.cooldown / s.spell.cooldown) + 1f)
                ).growX().uniformX().height(35f).row();
            }
        }).growX();

        return super.show();
    }
}
