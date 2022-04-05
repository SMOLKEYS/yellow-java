package yellow.interactions.ui;

import mindustry.ui.fragments.Fragment;
import arc.scene.*;
import arc.scene.actions.*;
import arc.flabel.*;

public class DialogueBox extends Fragment{
    private static final float width = 250f, height = 270f;
    
    public void build(Group parent){
        parent.row();
        parent.children.get(0).actions(Actions.moveBy(80f, 0f));
        parent.children.get(1).actions(Actions.moveBy(80f, 0f));
        
        parent.table(t -> {
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
