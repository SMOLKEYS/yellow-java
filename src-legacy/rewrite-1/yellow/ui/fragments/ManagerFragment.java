package yellow.ui.fragments;

import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.ui.*;

import static mindustry.Vars.*;

public class ManagerFragment implements CommonFragment{
    private boolean visibility = true;
    public Table managerTable;

    public void build(Group parent){
        parent.fill(t -> {
            t.name = "manager main";
            t.top().right();

            t.marginRight(150);

            t.table(p -> {
                p.table(Tex.pane, main -> {
                    managerTable = main;
                    main.top();
                    main.button("@yellow.weapons", () -> YellowVars.ui.weaponManager.show(player.unit().mounts)).growX().touchable(() -> player.unit().mounts.length != 0 ? Touchable.enabled : Touchable.disabled).row();
                    main.button("@yellow.spells", () -> YellowVars.ui.spellManager.show((MagicSpecialistEntity) player.unit(), ((MagicSpecialistEntity) player.unit()).spells)).growX().touchable(() -> player.unit() instanceof MagicSpecialistEntity e && e.spells.length != 0 ? Touchable.enabled : Touchable.disabled);
                }).size(350f, 190f).row();
                p.table(Styles.black5, click -> {
                    click.add("@yellow.manager");
                    click.clicked(() -> {
                        visibility = !visibility;
                        updatePosition(p, visibility);
                    });
                }).size(120f, 35f).right();
            }).get();

            t.visible(() -> player.unit() instanceof WeaponSpecialistEntity && ui.hudfrag.shown);
        });
    }

    private void updatePosition(Table t, boolean update){
        if(update){
            t.actions(Actions.translateBy(0, -(t.getMinHeight() - Scl.scl(35)), 0.7f, Interp.smooth));
        }else{
            t.actions(Actions.translateBy(0, t.getMinHeight() - Scl.scl(35), 0.7f, Interp.smooth));
        }
    }
}
