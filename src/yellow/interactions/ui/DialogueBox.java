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
    private static Table table = new Table().top().left();
    private static float width = 425f, height = 470f, x = 0f, y = isEnabled("mod-test-utils-enabled") ? 1490f : 1460f;
    private static String[] a;
    private static int cd = 0;
    private static boolean dialoguePlaying = false;
    private static Runnable[] scripts;
    private static int[] scriptPositions;
    
    public static void build(){
        ui.hudGroup.addChild(table);
        
        table.name = "box";
        
        table.table(t -> {
            t.background(Styles.flatDown);
            t.setSize(width, height);
            t.add(new Label("<no dialogue>"));
            t.getCells().get(0).grow().wrap();
            ((Label) t.getChildren().get(0)).setFontScale(0.67f);
        }).padBottom(y);
        table.button(b -> {
            b.setStyle(Styles.defaultb);
            b.setSize(24f, 24f);
        }, () -> next()).padBottom(y).right();
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
        ((Label) ((Table) table.getChildren().get(0)).getChildren().get(0)).setText(input[cd]);
        table.getChildren().get(1).touchable = Touchable.enabled;
        dialoguePlaying = true;
    }
    
    public static void dialogueEnd(){
        ((Label) ((Table) table.getChildren().get(0)).getChildren().get(0)).setText("...");
        table.getChildren().get(1).touchable = Touchable.disabled;
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
        ((Label) ((Table) table.getChildren().get(0)).getChildren().get(0)).setText(a[cd]);
        if(scripts.length == 0 || scriptPositions.length == 0) return;
        for(int i = 0; i < scriptPositions.length; i++){
            if(cd == scriptPositions[i]){
                app.post(scripts[i]);
            };
        };
    }
}
