package yellow.interactions.ui;

import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.scene.actions.*;
import arc.flabel.*;
import mindustry.ui.*;

import static mindustry.Vars.*;

public class DialogueBox{
    private static final float width = 250f, height = 270f;
    
    public void build(){
        ((Table) ui.hudGroup.find("minimap/position")).row();
        ((Table) ui.hudGroup.find("minimap/position")).getChildren().get(0).actions(Actions.moveBy(80f, 0f));
        ((Table) ui.hudGroup.find("minimap/position")).getChildren().get(1).actions(Actions.moveBy(80f, 0f));
        
        ((Table) ui.hudGroup.find("minimap/position")).table(t -> {
            t.name = "dialogue box";
            t.setSize(width, height);
            t.background(Styles.black6)
            
            t.add(new FLabel("{wave}....."));
        });
    }
}
