package yellow.interactions.ui;

import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.scene.actions.*;
import arc.flabel.*;
import mindustry.ui.*;
import mindustry.ui.fragments.Fragment;

import static mindustry.Vars.*;

public class DialogueBox{
    private Table table = new Table();
    private float width = 425f, height = 470f, x = 655f, y = 1490f;
    
    public static void build(){
        ui.hudGroup.addChild(table);
        
        table.name = "dialoguebox";
        
        table.setSize(width, height);
        table.setPosition(x, y);
        table.background(Styles.flatDown);
        table.add(new Label("..."));
    }
}
