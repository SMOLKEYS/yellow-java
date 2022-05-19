package yellow.interactions.ui;

import arc.*;
import arc.util.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.scene.event.*;
import arc.scene.actions.*;
import arc.flabel.*;
import mindustry.ui.*;
import mindustry.gen.*;

import static arc.Core.*;
import static mindustry.Vars.*;
import static yellow.util.YellowUtils.*;

public class DialogueBox{
    private static Table table = new Table();
    private static Table buttonTable = new Table();
    private static float width = 425f, height = 470f, x = 0f, y = isEnabled("mod-test-utils-enabled") ? 1460f : 1490f;
    private static String[] a;
    private static int cd = 0;
    private static boolean dialoguePlaying = false;
    private static Runnable[] scripts;
    private static int[] scriptPositions;
    
    public static void build(){
        ui.hudGroup.addChild(table);
        ui.hudGroup.addChild(buttonTable);
        
        table.name = "dialoguebox";
        
        table.setSize(width, height);
        table.setPosition(x, y);
        table.background(Styles.flatDown);
        table.add(new Label("..."));
        table.getCells().get(0).grow().wrap();
        table.margin(8f);
        ((Label) table.getChildren().get(0)).setFontScale(0.67f);
        
        buttonTable.name = "dialoguebox/button";
        
        buttonTable.update(() -> {
            buttonTable.setPosition(x + width, y);
        });
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
        dialogueStart(input, new Runnable[]{}, new int[]{});
    }
    
    public static void dialogueStart(String[] input, Runnable[] scriptIn, int[] positions){
        if(scriptIn.length != positions.length){
            Log.err(new IndexOutOfBoundsException("Variables scriptIn and positions must have the same length/size."));
            return;
        };
        if(dialoguePlaying){
            Log.warn("Dialogue attempted to play despite one currently playing now. Ignoring.");
            return;
        };
        a = input;
        if(scriptIn.length != 0 || positions.length != 0){
            scripts = scriptIn;
            scriptPositions = positions;
        };
        ((Label) table.getChildren().get(0)).setText(input[cd]);
        buttonTable.getChildren().get(0).touchable = Touchable.enabled;
        dialoguePlaying = true;
    }
    
    public static void dialogueEnd(){
        ((Label) table.getChildren().get(0)).setText("...");
        buttonTable.getChildren().get(0).touchable = Touchable.disabled;
        a = null;
        cd = 0;
        scripts = new Runnable[]{};
        scriptPositions = new int[]{};
        dialoguePlaying = false;
    }
    
    public static void next(){
        if(a.length - 2 < cd){
            dialogueEnd();
            return;
        };
        cd += 1;
        ((Label) table.getChildren().get(0)).setText(a[cd]);
        if(scripts.length == 0 || scriptPositions.length == 0) return;
        for(int i = 0; i < scriptPositions.length; i++){
            if(cd == scriptPositions[i]){
                app.post(scripts[i]);
            };
        };
    }
}
