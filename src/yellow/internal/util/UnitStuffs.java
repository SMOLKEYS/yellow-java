package yellow.internal.util;

import arc.scene.ui.layout.*;
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
}
