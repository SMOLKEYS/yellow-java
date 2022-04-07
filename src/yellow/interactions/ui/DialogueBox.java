package yellow.interactions.ui;

import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.scene.event.*;
import arc.scene.actions.*;
import arc.flabel.*;
import mindustry.ui.*;
import mindustry.ui.fragments.Fragment;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class DialogueBox{
    private static Table table = new Table();
    private static Table buttonTable = new Table();
    private static float width = 425f, height = 470f, x = 655f, y = 1490f;
    private static String[] a;
    private static int cd = 0;
    
    public static void build(){
        ui.hudGroup.addChild(table);
        ui.hudGroup.addChild(buttonTable);
        
        table.name = "dialoguebox";
        
        table.setSize(width, height);
        table.setPosition(x, y);
        table.background(Styles.flatDown);
        table.add(new Label("..."));
        
        buttonTable.name = "dialoguebox/button";
        
        buttonTable.setPosition(x, y);
        buttonTable.button(Icon.right, () -> {
            next();
        });
        buttonTable.getChildren().get(0).touchable = Touchable.disabled;
    }
    
    public static void hide(){
        table.actions(Actions.fadeOut(2f));
    }
    
    public static void show(){
        table.actions(Actions.fadeIn(0.4f));
    }
    
    public static void dialogueStart(String[] input){
        a = input;
        table.getChildren().get(0).remove();
        table.add(new Label(input[cd]));
        buttonTable.getChildren().get(0).touchable = Touchable.enabled;
    }
    
    public static void dialogueEnd(){
        table.getChildren().get(0).remove();
        table.add(new Label("..."));
        buttonTable.getChildren().get(0).touchable = Touchable.disabled;
        a = null;
        cd = 0;
    }
    
    public static void next(){
        if(a.length < cd){
            dialogueEnd();
            return;
        };
        cd += 1;
        table.getChildren().get(0).remove();
        table.add(new Label(a[cd]));
    }
}
