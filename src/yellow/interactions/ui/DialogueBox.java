package yellow.interactions.ui;

import mindustry.ui.fragments.Fragment;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.scene.actions.*;
import arc.flabel.*;

public class DialogueBox extends Fragment{
    private static final float width = 250f, height = 270f;
    
    public void build(Group parent){
        parent.find("minimap/position").row();
        parent.find("minimap/position").children.get(0).actions(Actions.moveBy(80f, 0f));
        parent.find("minimap/position").children.get(1).actions(Actions.moveBy(80f, 0f));
        
        parent.find("minimap/position").table(t -> {
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
