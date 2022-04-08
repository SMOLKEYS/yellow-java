package yellow.ui.dialogs;

import arc.util.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;

import static mindustry.Vars.*;

public class DialogueBoxEditorDialog extends BaseDialog{
    private static Table dBox = ui.hudGroup.find("dialoguebox");
    
    public DialogueBoxEditorDialog(){
        super("Dialogue Box Editor");
        
        addCloseButton();
        
        cont.add("[scarlet]Attempting to use letters or double-periods (..) can potentially crash the game![]").row();
        
        cont.add("Box X Position (def 655): ");
        cont.area("", xbox -> {
            dBox.x = Strings.parseFloat(xbox);
        }).row();
        
        cont.add("Box Y Position (def 1490): ");
        cont.area("", ybox -> {
            dBox.y = Strings.parseFloat(ybox);
        }).row();
        
        cont.add("Box Margin (def 8): ");
        cont.area("", boxmargin -> {
            dBox.margin(Strings.parseFloat(boxmargin));
        }).row();
        
        cont.add("Box Width (def 425): ");
        cont.area("", boxwidth -> {
            dBox.setWidth(Strings.parseFloat(boxwidth));
        }).row();
        
        cont.add("Box Height (def 470): ");
        cont.area("", boxheight -> {
            dBox.setHeight(Strings.parseFloat(boxheight));
        }).row();
        
        cont.add("Text Size (def 0.67):");
        cont.area("", txtsize -> {
            ((Label) dBox.getChildren().get(0)).setFontScale(Strings.parseFloat(txtsize));
        }).row();;
        
    }
}
