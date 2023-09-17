package yellow.internal.util;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;

public class UnitStuffs {

    public static void unitBar(Table parent, UnitType type, String rightHandText){
        unitBar(parent, type, rightHandText, -1f);
    }

    public static void unitBar(Table parent, UnitType type, String rightHandText, float width){
        YellowUtils.table(parent, c -> {
            if(width < 0f){
                c.growX();
            }else{
                c.width(width);
            }
            c.height(65f);
        }, cc -> {
            cc.setBackground(Styles.grayPanel);
            cc.image(type.uiIcon).size(40f).padLeft(20f);
            cc.add(type.localizedName).grow().left().pad(10f);
            cc.add(rightHandText).pad(25f);
        });
    }

    public static void foodOpts(Table parent, Unit unit, Cons<Cell<Table>> table, Cons<Table> tableChildren){
        YellowUtils.table(parent, c -> {
            c.width(Core.graphics.getWidth());
            c.height(115);
            table.get(c);
        }, cc -> {
            cc.setBackground(Tex.pane);
            cc.image(unit.type.uiIcon).size(50).padLeft(20f);
            Cell<Label> suse = cc.add(unit.type.localizedName + "\n" + Mathf.round(unit.health) +  "/" + Mathf.round(unit.maxHealth)).grow().left().pad(15f);
            suse.update(up -> {
                if(unit.dead || !unit.isValid()){
                    up.setText("[red]DEAD/INVALID[]");
                    cc.getChildren().each(el -> {
                        el.touchable = Touchable.disabled;
                    });
                }else{
                    up.setText(unit.type.localizedName + "\n" + Mathf.round(unit.health) +  "/" + Mathf.round(unit.maxHealth));
                }
            });
            tableChildren.get(cc);
        });
    }
}
