package yellow.ui.fragments;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import yellow.*;
import yellow.ui.*;
import yellow.util.*;

import static mindustry.Vars.*;

public class ManagerFragment implements CommonFragment{
    private boolean visibility = false, highlight = true;
    public Table managerTable;

    @Override
    public void build(Group parent){
        parent.fill(t -> {
            t.name = "manager main";
            t.top().right();

            t.marginRight(150);

            t.table(p -> {
                p.table(Tex.pane, main -> {
                    managerTable = main;
                    main.top();
                    main.button("@yellow.weapons", () -> YellowVars.weapons.show(player.unit().mounts)).growX().touchable(() -> player.unit() != null && player.unit().mounts.length != 0 ? Touchable.enabled : Touchable.disabled).row();
                    if(Yellow.debug){
                        main.button("@yellow.weapons-debug", () -> {
                            for(WeaponMount mnt : player.unit().mounts()) mnt.reload = 0f;
                        }).growX().row();
                        main.button("@yellow.manager-reset", () -> {
                            Core.settings.put("yellow-first-control", true);
                            Core.settings.put("yellow-first-control-notif", false);
                        }).growX().row();
                    }
                }).size(350f, 190f).row();

                p.table(Styles.black5, click -> {
                    Label text = click.add("@yellow.manager").get();
                    Action forever = Actions.forever(Actions.sequence(Actions.color(Pal.accent, 0.7f), Actions.color(Color.white, 0.7f)));
                    highlight = Core.settings.getBoolOnce("yellow-first-control");

                    if(highlight){
                        text.addAction(forever);
                        text.update(() -> {
                            Core.settings.getBoolOnce("yellow-first-control-notif", () -> YellowVars.notifrag.showPersistentNotification(Core.bundle.get("yellow.manager-hint")));
                        });
                    }

                    click.clicked(() -> {
                        visibility = !visibility;
                        updatePosition(p, visibility);

                        if(highlight){
                            highlight = false;
                            text.removeAction(forever);
                            text.update(() -> {});
                            text.addAction(Actions.sequence(Actions.color(Pal.heal), Actions.color(Color.white, 2f)));
                        }
                    });
                }).size(120f, 35f).right();
            }).get();

            t.y = visibility ? 0 : t.getMinHeight() - Scl.scl(35);

            t.visible(() -> Validator.hasToggleWeapons(player.unit()) && ui.hudfrag.shown);
        });
    }

    private void updatePosition(Table t, boolean update){
        if(update){
            t.actions(Actions.translateBy(0, -(t.getMinHeight() - Scl.scl(35))));
        }else{
            t.actions(Actions.translateBy(0, t.getMinHeight() - Scl.scl(35)));
        }
    }
}
