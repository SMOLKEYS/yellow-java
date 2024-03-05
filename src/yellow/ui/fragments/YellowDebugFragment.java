package yellow.ui.fragments;

import arc.graphics.*;
import arc.scene.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.ui.*;
import yellow.*;
import yellow.entities.units.entity.*;
import yellow.util.*;

public class YellowDebugFragment{

    public Team team = Team.sharded;

    public void build(Group parent){
        //debug menu
        parent.fill(t -> {
            t.name = "yellow debug";
            t.visible(() -> Yellow.debug);
            t.center().right();

            t.table(b -> {
                b.name = "guh";
                b.background(Styles.black8);
                b.defaults().width(150);

                b.add("yellow debug mode\ndo not complain about the implementation").update(label -> label.setColor(YellowUtils.pulse(Color.yellow, 10f))).row();
                b.add("").update(label -> {
                    label.setText("yellow: " + team.name);
                    label.setColor(YellowUtils.pulse(team.color, 10f));
                });
                b.button("control yellow", () -> {
                    YellowUnitEntity u = YellowUtils.getActiveYellow(team);

                    if(u != null) YellowVars.ui.yellowControl.show(u.mounts(), u.spells(), u);
                }).get().getLabel().setWrap(false);
                b.row();
                b.button("kill yellow", () -> {
                    YellowUnitEntity u = YellowUtils.getActiveYellow(team);

                    u.kill();
                }).get().getLabel().setWrap(false);
                b.row();
                b.button("instakill yellow", () -> {
                    YellowUnitEntity u = YellowUtils.getActiveYellow(team);

                    u.forceKill();
                }).get().getLabel().setWrap(false);
                b.row();
                b.button("despawn", () -> {
                    YellowUnitEntity u = YellowUtils.getActiveYellow(team);

                    u.despawn();
                }).get().getLabel().setWrap(false);
                b.row();
                b.button("spell force", () -> {
                    YellowUnitEntity u = YellowUtils.getActiveYellow(team);

                    for(var s: u.spells()){
                        s.cast(u);
                    }
                }).get().getLabel().setWrap(false);
                b.row();
                b.area("team", te -> {
                    var val = Strings.parseInt(te);

                    team = Team.get(val == Integer.MIN_VALUE ? 0 : Math.min(val, 256));
                });
            });
        });
    }
}
