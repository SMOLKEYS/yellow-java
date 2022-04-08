package yellow.ui.dialogs;

import arc.util.*;
import arc.scene.ui.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;

import static mindustry.Vars.*;

public class DialogueBoxEditorDialog extends BaseDialog{
    private static Table dBox = ui.hudGroup.find("dialoguebox");
    
    public DialogueBoxEditorDialog(){
        super("Dialogue Box Editor");
        
        addCloseButton();
        
        cont.area("Box X", xbox -> {
            if(xbox.replaceAll("\\s", "") == ""){
                dBox.x = 655f;
                return;
            };
            dBox.x = Strings.parseFloat(xbox.replaceAll("[^0-9]", ""));
        }).row();
        
        cont.area("Box Y", ybox -> {
            if(ybox.replaceAll("\\s", "") == ""){
                dBox.y = 655f;
                return;
            };
            dBox.y = Strings.parseFloat(ybox.replaceAll("[^0-9]", ""));
        }).row();
        
        cont.area("Box Margin", boxmargin -> {
            if(boxmargin.replaceAll("\\s", "") == ""){
                dBox.margin(8f);
                return;
            };
            dBox.margin(Strings.parseFloat(boxmargin.replaceAll("[^0-9]", "")));
        }).row();
        
        cont.area("Box Width", boxwidth -> {
            if(boxwidth.replaceAll("\\s", "") == ""){
                dBox.setWidth(425f);
                return;
            };
            dBox.setWidth(Strings.parseFloat(boxwidth.replaceAll("[^0-9]", "")));
        }).row();
        
        cont.area("Box Height", boxheight -> {
            if(boxheight.replaceAll("\\s", "") == ""){
                dBox.setHeight(470f);
                return;
            };
            dBox.setHeight(Strings.parseFloat(boxheight.replaceAll("[^0-9]", "")));
        }).row();
    }
}
