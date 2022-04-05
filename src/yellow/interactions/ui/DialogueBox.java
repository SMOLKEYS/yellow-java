package yellow.interactions.ui;

import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.scene.actions.*;
import arc.flabel.*;

import static mindustry.Vars.*;

public class DialogueBox{
    private static final float width = 250f, height = 270f;
    
    public void build(){
        ui.hudGroup.find("minimap/position").class.row();
        ui.hudGroup.find("minimap/position").class.children.get(0).actions(Actions.moveBy(80f, 0f));
        ui.hudGroup.find("minimap/position").class.children.get(1).actions(Actions.moveBy(80f, 0f));
        
        ui.hudGroup.find("minimap/position").class.table(t -> {
            t.name = "dialogue box";
            t.size(width, height);
            
            t.label(l -> {
                l.name = "dialogue text";
                l.setWrap(true);
                l.setText(new FLabel("{wave}..."));
            });
        });
    }
}
